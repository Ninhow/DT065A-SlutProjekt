package com.slutprojekt.models;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class MqttClientIot {


    public String id;
    public MqttClient mqttClient;

    public MqttClientIot(String id, MqttClient mqttClient){
        this.id = id;
        this.mqttClient = mqttClient;
    }
    
    // byte[] buffer;


    // public MqttClient(Socket s, DataInputStream dis, DataOutputStream dos, String socketId) throws IOException{
    //     this.s = s;
    //     this.dis = dis;
    //     this.dos = dos;
    //     this.socketId = socketId;
    // }

    // @Override
    // public void run(){
    //     while(true) {
    //         buffer = new byte[1024];
    //         try {
    //             dis.read(buffer);
    //         } catch (Exception e) {
    //             //TODO: handle exception
    //         }
    //     }
    // }

    // public DataInputStream getDis() {
    //     return dis;
    // }

    // public DataOutputStream getDos() {
    //     return dos;
    // }

    // public Socket getSocket() {
    //     return s;
    // }
    
}
