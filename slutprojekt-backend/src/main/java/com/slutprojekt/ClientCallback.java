package com.slutprojekt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class ClientCallback implements MqttCallback{

    private WebSocketSession session;

    public ClientCallback(WebSocketSession session){
        this.session = session;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection Lost: " + cause);
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Topic: " + topic);
        System.out.println("Message: " + message);
        session.sendMessage(new TextMessage(message.toString()));
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Delivery complete: " + token);
        
    }
    
}
