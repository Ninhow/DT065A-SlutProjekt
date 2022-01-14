package com.mqtt.classes;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class SubscriptionHandler {
    private final ReentrantLock lock = new ReentrantLock();
    String subscriptionString;
    private ArrayList<Socket> clients = new ArrayList<>();
    byte[] retain;

    public SubscriptionHandler() {
    }

    public SubscriptionHandler(String subscriptionString) {
        this.subscriptionString = subscriptionString;
    }

    public SubscriptionHandler(Socket client) {
        clients.add(client);
    }

    public void addClient(Socket client) {
        this.clients.add(client);
    }

    public void removeClient(Socket client) {
        try {
            lock.lock();
            // for (Socket socket : clients) {
            // if (socket == client) {
            // clients.remove(socket);
            // }
            // }
            int index = -1;
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i) == client) {
                    index = i;
                }
            }
            if (index != -1) {
                clients.remove(index);
            }
        } finally {
            lock.unlock();
        }
    }

    public void listClientsAdresses() {
        try {
            lock.lock();
            for (Socket client : clients) {
                System.out.println(client.getLocalSocketAddress());
            }
        } finally {
            lock.unlock();
        }
    }

    public ArrayList<Socket> getClients() {
        return clients;
    }

    public Boolean hasTopicClient(Socket socket) {
        try {
            lock.lock();
            for (Socket client : clients) {
                if (client == socket) {
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void publishMessage(byte[] message, int remainingLength, Boolean ret) throws IOException {
        try {
            lock.lock();
            if (ret) {
                this.retain = Arrays.copyOfRange(message, 0, remainingLength);
            }
            for (Socket client : clients) {
                OutputStream os = client.getOutputStream();
                os.write(Arrays.copyOfRange(message, 0, remainingLength));
                os.flush();
            }
        } finally {
            lock.unlock();
        }
    }

    public void sendRetain(Socket socket) throws IOException {
        try {
            lock.lock();
            if (this.retain != null) {
                for (Socket client : clients) {
                    if (client == socket) {
                        // System.out.println("SIZE RETAIN: " + retain.size());
                        OutputStream os = socket.getOutputStream();
                        // byte[] toSend = Arrays.copyOfRange(this.retain, 0, msg.length);
                        os.write(this.retain);
                        os.flush();
                    }

                }
            }

        } finally {
            lock.unlock();
        }
    }

}
