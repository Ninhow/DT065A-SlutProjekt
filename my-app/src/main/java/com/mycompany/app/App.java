package com.mycompany.app;

import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class App 
{
    public static void main( String[] args ) throws MqttException
    {
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        
        
        // clients.add(new MqttClient("tcp://172.24.207.84:1883", session.getId()));
        MqttClient client = new MqttClient("tcp://172.23.2.75:1883", "MqttCoap", memoryPersistence);
        ClientCallback clientCall = new ClientCallback(client);
        MqttConnectOptions connOptions = new MqttConnectOptions();
        client.setCallback(clientCall);
        connOptions.setKeepAliveInterval(60);
        connOptions.setCleanSession(true);
        client.connect();
        client.subscribe("coap");
        client.subscribe("sensor");


    }
}
