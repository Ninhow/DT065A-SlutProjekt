package com.mycompany.app;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.util.HashMap;
import java.util.Map;

import com.mycompany.app.models.*;
public class ClientCallback implements MqttCallback{

    MqttClient mqttClient;
    public ClientCallback(){

    }

    public ClientCallback(MqttClient client){
        this.mqttClient = client;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Cause: " + cause.getMessage());
        System.out.println("Connection lost");
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("smth: " + message.getPayload().toString());
        System.out.println("Topic: " + topic);
        if(topic.equals("coap")){
            System.out.println(message.getPayload());
            System.out.println("TEST");
            UDPClient coapClient = new UDPClient("172.23.0.1");
            Map<OptionCode, String> options = new HashMap<OptionCode, String>();
            options.put(OptionCode.URI_PATH, "temp");
            Message get = new Message(1, TypeCode.CON, 0, ResponseCode.GET, 1, options, "");
            String messageRes = coapClient.sendMessage(get);
            final MqttMessage test = new MqttMessage(messageRes.getBytes());
            test.setQos(0);
            test.setRetained(true);
            Thread thread = new Thread() {
                public void run() {
                    try {
                        mqttClient.publish("sensor", test);
                    } catch (MqttPersistenceException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }  
             };
             thread.start(); 
        }else{

        }
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
    
}
