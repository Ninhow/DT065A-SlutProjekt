package com.mqtt.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import com.mqtt.models.Message;
import com.mqtt.models.MessageType;

public class Parser {

    public Message readConnectMessage(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int remainingLength = messageStream[1];
        int MSB_LSB = ((messageStream[2] & 0xff) << 8) | (messageStream[3] & 0xff);
        byte[] mqtt = Arrays.copyOfRange(messageStream, 5, 8);
        int level = messageStream[9];
        int flags = messageStream[10];
        int keepAlive = ((messageStream[11] & 0xff) << 8) | (messageStream[12] & 0xff);
        byte[] payload = Arrays.copyOfRange(messageStream, 12, messageStream[1] + 2);
        return new Message(messageType, remainingLength, MSB_LSB, mqtt, level, flags, keepAlive,
                new String(payload, StandardCharsets.UTF_8));

    }

    public int connectionType(byte b) {
        return ((b & 0xFF) & 0xF0) >> 4;
    }

    public Message readPublishMessage(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int DUPFlag = ((messageStream[0] & 0x08) >> 3); // (0x08)
        int QoSLevel = ((messageStream[0] & 0x06) >> 1); // (0x06)
        int Retrain = (messageStream[0] & 0x01); // (0x06)
        int remainingLength = messageStream[1];
        int topicLength = ((messageStream[2] & 0xff) << 8) | (messageStream[3] & 0xff);
        byte[] topicChars = Arrays.copyOfRange(messageStream, 4, 4 + topicLength);
        int start = (4 + topicLength);
        int end = (6 + topicLength);

        byte[] packetIndetifier = Arrays.copyOfRange(messageStream, start, end);
        if (QoSLevel == 0) {
            if ((3 + topicLength) == (remainingLength + 2)) {
                return new Message(messageType, DUPFlag, QoSLevel, Retrain, remainingLength, topicLength, topicChars,
                        null,
                        null);
            } else {
                byte[] payload = Arrays.copyOfRange(messageStream, 4 + topicLength, messageStream[1] + 2);
                return new Message(messageType, DUPFlag, QoSLevel, Retrain, remainingLength, topicLength, topicChars,
                        null,
                        new String(payload, StandardCharsets.UTF_8));
            }
        } else {
            if ((6 + topicLength) == (remainingLength + 2)) {
                return new Message(messageType, DUPFlag, QoSLevel, Retrain, remainingLength, topicLength, topicChars,
                        packetIndetifier,
                        null);
            } else {
                byte[] payload = Arrays.copyOfRange(messageStream, 6 + topicLength, remainingLength + 2);
                return new Message(messageType, DUPFlag, QoSLevel, Retrain, remainingLength, topicLength, topicChars,
                        packetIndetifier,
                        new String(payload, StandardCharsets.UTF_8));
            }
        }
    }

    // Det saknas identifier i själva message?
    public Message readSubscription(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int remainingLength = messageStream[1];
        byte[] MSB_LSB = Arrays.copyOfRange(messageStream, 2, 4);

        int index = 4;
        StringBuilder strBuilder = new StringBuilder();
        ArrayList<Integer> qualityOfServices = new ArrayList<Integer>();
        ArrayList<String> topics = new ArrayList<String>();

        while (index != messageStream[1] + 2) {
            int lengtOfTopic = ((messageStream[index] & 0xff) << 8) | (messageStream[index + 1] & 0xff);
            index += 2;

            for (int i = index; i < (index + lengtOfTopic); i++) {
                strBuilder.append((char) messageStream[i]);
            }
            topics.add(strBuilder.toString());
            index += lengtOfTopic;
            qualityOfServices.add((int) messageStream[index]);
            index++;
        }

        return new Message(messageType, remainingLength, MSB_LSB, topics, qualityOfServices);
    }

    public Message readUnsubscribeMessage(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int remainingLength = messageStream[1];
        byte[] MSB_LSB = Arrays.copyOfRange(messageStream, 2, 4);

        int index = 4;
        StringBuilder strBuilder = new StringBuilder();
        ArrayList<String> topics = new ArrayList<String>();

        while (index != messageStream[1] + 2) {
            int lengtOfTopic = ((messageStream[index] & 0xff) << 8) | (messageStream[index + 1] & 0xff);
            index += 2;

            for (int i = index; i < (index + lengtOfTopic); i++) {
                strBuilder.append((char) messageStream[i]);
            }
            topics.add(strBuilder.toString());
            index += lengtOfTopic;
        }

        return new Message(messageType, remainingLength, MSB_LSB, topics);
    }

    public Message readPingRequest(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int remainingLength = messageStream[1];
        return new Message(messageType, remainingLength);
    }

    public Message readDisconnectMessage(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int remainingLength = messageStream[1];
        return new Message(messageType, remainingLength);
    }

    public Message readPubrel(byte[] messageStream) {
        MessageType messageType = MessageType.CodeName(((messageStream[0] & 0xFF) & 0xF0) >> 4);
        int remainingLength = messageStream[1];
        byte[] MSB_LSB = Arrays.copyOfRange(messageStream, 2, 4);

        return new Message(messageType, remainingLength, MSB_LSB);
    }

}
