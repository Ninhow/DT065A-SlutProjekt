package com.mqtt.classes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mqtt.models.Message;
import com.mqtt.utils.Parser;

public class Broker {
    Parser parser = new Parser();
    ArrayList<Socket> connectedClients = new ArrayList<>();
    Map<String, SubscriptionHandler> topics = new HashMap<>();

    public Broker() {
        try (ServerSocket serverSocket = new ServerSocket(1883)) {

            System.out.println("Server is listening on port " + 1883);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                Thread t = new Client(socket, new DataInputStream(new BufferedInputStream(socket.getInputStream())),
                        new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())), this);
                t.start();

                connectedClients.add(socket);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void handleMessage(byte[] bytes, Socket client, DataOutputStream dos) throws IOException {

        int messageType = parser.connectionType(bytes[0]);

        switch (messageType) {
            case 1:
                dos.write(Acknowledgement(messageType));
                dos.flush();

                break;
            case 3:
                parser.readPublishMessage(bytes);
                Message publishMsg = parser.readPublishMessage(bytes);
                // topics.put(new String(publishMsg.getTopicChars(), StandardCharsets.UTF_8),
                // new SubscriptionHandler());
                System.out.println(publishMsg.getMessage());
                Boolean retain = false;
                System.out.println("RETAIN NUMBER: " + publishMsg.getRetain());
                if (publishMsg.getRetain() == 1) {
                    retain = true;
                }
                publishMessage(bytes, new String(publishMsg.getTopicChars(), StandardCharsets.UTF_8),
                        publishMsg.getRemainingLength() + 2, retain);
                if (publishMsg.getQoSLevel() == 0) {

                } else if (publishMsg.getQoSLevel() == 1) {
                    System.out.println("PUB ACK QOS 1");
                    dos.write(Acknowledgement(messageType, publishMsg.getPacketIdentifier()));
                    dos.flush();
                } else {

                }
                printAllTopics();

                break;

            case 12:
                parser.readPingRequest(bytes);
                dos.write(Acknowledgement(messageType));
                dos.flush();
                System.out.println("PING ACk");
                break;

            case 8:
                Message subscribeMessage = parser.readSubscription(bytes);
                System.out.println(subscribeMessage.getMessage());
                subscribeTopic(subscribeMessage.getTopics(), client);
                dos.write(Acknowledgement(messageType, subscribeMessage.getMSG_LSB()));
                dos.flush();
                sendRetain(subscribeMessage.getTopics(), client);
                break;
            case 10:
                Message unsubscribeMessage = parser.readUnsubscribeMessage(bytes);
                System.out.println(unsubscribeMessage.getMessage());

                dos.write(Acknowledgement(messageType, unsubscribeMessage.getMSG_LSB()));
                dos.flush();
                break;

            case 14:
                System.out.println("DISCONNECT");
                disconnectSocket(client);
                Message readDisconnect = parser.readDisconnectMessage(bytes);
                System.out.println(readDisconnect.getMessage());
                break;
            default:
                break;
        }
    }

    public byte[] Acknowledgement(int messageType) {
        if (messageType == 1) { // Connect acknowledgement
            return new byte[] { (byte) 0b00100000, (byte) 0b00000010, (byte) 0x0, (byte) 0x0 };
        } else if (messageType == 12) { // Ping response
            return new byte[] { (byte) 0b11010000, (byte) 0x0 };
        }
        return null;
    }

    public byte[] Acknowledgement(int messageType, byte[] packetID) {
        if (messageType == 3) {
            return new byte[] { (byte) 0b01000000, (byte) 0x02, packetID[0], (byte) packetID[1] };
        } else if (messageType == 8) {
            return new byte[] { (byte) 0b10010000, (byte) 0b00000011, (byte) packetID[0], (byte) packetID[1],
                    0x0 };
        } else if (messageType == 10) {
            return new byte[] { (byte) 0b10110000, (byte) 0b00000010, (byte) packetID[0],
                    (byte) packetID[1] };
        }
        return null;
    }

    // TEST
    public void printAllTopics() {
        for (String key : topics.keySet()) {
            System.out.println("Topics: " + key);
        }
    }

    public void disconnectSocket(Socket clientSocket) throws IOException {
        for (SubscriptionHandler sh : topics.values()) {
            sh.removeClient(clientSocket);
        }
        for (int i = 0; i < connectedClients.size(); i++) {
            if (clientSocket == connectedClients.get(i)) {
                System.out.println("in i ifsatsen");
                connectedClients.get(i).close();
                connectedClients.remove(i);
                break;
            }
        }
    }

    public void subscribeTopic(ArrayList<String> subs, Socket client) {
        System.out.println("Number of Topics: " + topics.size());
        for (String topic : subs) {
            if (topics.containsKey(topic)) {
                SubscriptionHandler sh = topics.get(topic);
                if (!sh.hasTopicClient(client)) {
                    sh.addClient(client);
                }
            } else {
                topics.put(topic, new SubscriptionHandler(client));
            }
        }

        System.out.println("Number of Topics: " + topics.size());
    }

    public void publishMessage(byte[] message, String topic, int remainingLength, Boolean retain) throws IOException {

        SubscriptionHandler sh = topics.get(topic);
        if (sh == null) {
            topics.put(topic, new SubscriptionHandler());
            SubscriptionHandler newSH = topics.get(topic);
            newSH.publishMessage(message, remainingLength, retain);

        } else {
            sh.publishMessage(message, remainingLength, retain);

        }
    }

    public void sendRetain(ArrayList<String> subTopics, Socket socket) throws IOException {
        for (String msg : subTopics) {
            SubscriptionHandler sh = topics.get(msg);
            sh.sendRetain(socket);
        }
    }
}
