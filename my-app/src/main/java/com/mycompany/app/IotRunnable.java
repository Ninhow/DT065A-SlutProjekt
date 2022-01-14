package com.mycompany.app;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class IotRunnable implements Runnable{
    MqttClient client;
    MqttMessage message;

    public IotRunnable(MqttClient client, MqttMessage message){
        this.client = client;
        this.message = message;
    }


    @Override
    public void run() {
        
        try {
            message.setQos(0);
            message.setRetained(true);
            client.publish("sensor", message);
        } catch (MqttPersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }}
