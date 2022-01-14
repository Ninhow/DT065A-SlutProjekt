package com.slutprojekt;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GreetingHandler extends TextWebSocketHandler {
    private ArrayList<MqttClient> clients = new ArrayList<>();
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException, MqttPersistenceException, MqttException {
        if(message.getPayload().equals("sensor")){
            for(MqttClient client : clients){
                System.out.println("Current client: " + client.getClientId());
                System.out.println("Current session: " + session.getId());
                if(client.getClientId().equals(session.getId())){
                    System.out.println("test");
                    MqttMessage msg = new MqttMessage(message.getPayload().getBytes());
                    msg.setQos(0);
                    client.publish("coap", msg);
                }
            }
        }
        System.out.println("Handle text message körs: " + message.getPayload());
        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
        // session.sendMessage(msg);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        try {
            System.out.println("New Session: " + session.getId());
            MemoryPersistence memoryPersistence = new MemoryPersistence();
            ClientCallback clientCallback = new ClientCallback(session);
            // clients.add(new MqttClient("tcp://172.24.207.84:1883", session.getId()));
            MqttClient client = new MqttClient("tcp://172.23.2.75:1883", session.getId(), memoryPersistence);
            MqttConnectOptions connOptions = new MqttConnectOptions();
            client.setCallback(clientCallback);
            connOptions.setCleanSession(true);
            client.connect();
            client.subscribe("sensor");
            clients.add(client);
            session.sendMessage(new TextMessage("Hello, " + session.getId() + "!"));
        } catch (Exception e) {
            System.out.println("bla");
        }
        
    }

    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for(MqttClient client : clients){
            if(client.getClientId().equals(session.getId())){
                System.out.println("test");
                client.disconnect();
                client.close();
            }
        }
        session.close();
	}

}
