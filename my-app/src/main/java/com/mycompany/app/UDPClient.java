package com.mycompany.app;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mycompany.app.models.Message;
import com.mycompany.app.models.OptionCode;
import com.mycompany.app.models.TypeCode;
import com.mycompany.app.utils.CoapParser;

public class UDPClient {
    
    private DatagramSocket socket;
    private InetAddress adress;
    private CoapParser coapParser = new CoapParser();
    private byte[] sendBuffer;
    private byte[] receiveBuffer;

    public UDPClient(String url){
        try {
            adress = InetAddress.getByName(url);
            socket = new DatagramSocket();
            socket.connect(adress, 5683);
        } catch (SocketException | UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
    }

    public String sendMessage(Message msg) {

                                //Header     //Type      //M  E S S A G E   |   //Options       
        byte [] sendBuffer = {(byte)0x40 , (byte)0x01, (byte)0x04, (byte)0xd2, (byte)0xb4, (byte)0x74, (byte)0x65, (byte)0x73, (byte)0x74};
        byte[] binaryMsg = toCoap(msg);
        DatagramPacket sendPayload = new DatagramPacket(binaryMsg, binaryMsg.length);
        String message = "";
        try {
            socket.send(sendPayload);
            receiveBuffer = new byte[512];
            DatagramPacket responsePayload = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            System.out.println(responsePayload.getLength());

            socket.receive(responsePayload);
            Message test = coapParser.parseMessage(responsePayload.getData(), responsePayload.getLength());
            message = test.getPayload();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return message;
    }

    public byte[] toCoap(Message message) {
        List<Byte> temp = new ArrayList<Byte>();
        
        temp.add((byte) ((message.getVersion() << 6) | (message.getTypeCode().getInteger() << 4)| (message.getTokenLength())));
        temp.add((byte) (int) message.getResponseCode().getInteger());
        
        byte[] messageID = BigInteger.valueOf(message.getMessageID()).toByteArray();
        
        if(messageID.length == 1) {
            temp.add((byte) 0x00);
            temp.add(messageID[0]);
        } else {
            temp.add(messageID[0]);
            temp.add(messageID[1]);
        }
        
        for(Map.Entry<OptionCode,String> entry : message.getOptions().entrySet()) {
            temp.add((byte) ((int) entry.getKey().getInteger() << 4 | (entry.getValue().length())));
            for(char character : entry.getValue().toCharArray()) {
                temp.add((byte) character);
            }
        }

        if(!message.getPayload().isEmpty()) {
            temp.add((byte) 0xFF);
            for(char character : message.getPayload().toCharArray()) {
                temp.add((byte) character);
            }
        }
        
        byte[] coapMessage = new byte[temp.size()];
        for(int i = 0; i < temp.size(); i++) {
            coapMessage[i] = (byte) temp.get(i);
        }
        
        return coapMessage;
    }
}
