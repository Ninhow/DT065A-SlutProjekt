package com.mqtt.models;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Message {

    int remainingLength;
    int MSB_LSB;
    byte[] MSG_LSB;

    byte[] mqtt;
    int level;
    int flags;
    int keepAlive;
    String payload;
    byte[] packetIdentifier;
    byte[] topicChars;
    int topicLength;
    int Retain;
    int QoSLevel;
    int DUPFlag;
    ArrayList<String> topicFilters;
    ArrayList<Integer> qualityOfServices;

    public Message(MessageType messageType, int remainingLength, byte[] MSB_LSB) {
        this.messageType = messageType;
        this.remainingLength = remainingLength;
        this.MSG_LSB = MSB_LSB;
    }

    public Message(MessageType messageType, int remainingLength) {
        this.messageType = messageType;
        this.remainingLength = remainingLength;
    }

    public Message(MessageType messageType, int remainingLength, int MSB_LSB, byte[] mqtt, int level, int flags,
            int keepAlive, String payload) {
        this.messageType = messageType;
        this.remainingLength = remainingLength;
        this.MSB_LSB = MSB_LSB;
        this.mqtt = mqtt;
        this.level = level;
        this.flags = flags;
        this.keepAlive = keepAlive;
        this.payload = payload;
    }

    public Message(MessageType messageType, int DUPFlag, int QoSLevel, int Retain, int remainingLength,
            int topicLength, byte[] topicChars, byte[] packetIdentifier, String payload) {
        this.messageType = messageType;
        this.DUPFlag = DUPFlag;
        this.QoSLevel = QoSLevel;
        this.Retain = Retain;
        this.remainingLength = remainingLength;
        this.topicLength = topicLength;
        this.topicChars = topicChars;
        this.packetIdentifier = packetIdentifier;
        this.payload = payload;
    }

    public Message(MessageType messageType, int remainingLength, byte[] MSB_LSB, ArrayList<String> topics,
            ArrayList<Integer> qualityOfServices) {
        this.messageType = messageType;
        this.remainingLength = remainingLength;
        this.MSG_LSB = MSB_LSB;
        this.topicFilters = topics;
        this.qualityOfServices = qualityOfServices;
    }

    public String getMessageType() {
        return messageType.toString();
    }

    public Message(MessageType messageType, int remainingLength, byte[] MSB_LSB, ArrayList<String> topicFilters) {
        this.messageType = messageType;
        this.remainingLength = remainingLength;
        this.MSG_LSB = MSB_LSB;
        this.topicFilters = topicFilters;
    }

    public String getMessage() {
        String message = "test";
        switch (this.messageType.getInteger()) {
            case 1:
                message = "Connection type: " + messageType.toString() + "\n" + "Remaining Length: " + remainingLength
                        + "\n" + "Length of protocol name length: " + MSB_LSB + "\n" + "Protocol name: "
                        + new String(mqtt, StandardCharsets.UTF_8) + "\n" + "Level: " + level + "\n" + "Flags: " + flags
                        + "\n" + "Keep alive(s): " + keepAlive + "\n" + "Payload: " + payload + "\n";
                break;
            case 3:
                Integer identifier = null;
                if (packetIdentifier != null) {
                    identifier = ((packetIdentifier[0] & 0xff) << 8) | (packetIdentifier[1] & 0xff);
                }
                message = "\nConnection type: " + messageType.toString() + "\n" + "DUP Flag: " + DUPFlag +
                        "\nQoS level: " + QoSLevel + "\nRetain: " + Retain + "\nRemaining Length: " + remainingLength
                        + "\n" + "Topic Length: " + topicLength + "\n" + "Topic Characters: "
                        + new String(topicChars, StandardCharsets.UTF_8) +
                        "\n" + "Packet Identifier: " + identifier + "\n Payload: " + payload;
                break;
            case 10:
                message = "Connection type: " + messageType.toString() + "\n" + "Remaining Length: " + remainingLength
                        + "\n" + "PackageID: " + MSB_LSB + "\n";
                for (int i = 0; i < topicFilters.size(); i++) {
                    message += "Topic " + i + ": " + topicFilters.get(i) + "\n";
                }
                break;
            case 8:
                message = "Connection type: " + messageType.toString() + "\n" + "Remaining Length: " + remainingLength
                        + "\n" + "PackageID: " + MSB_LSB + "\n";
                for (int i = 0; i < topicFilters.size(); i++) {
                    message += "Topic " + i + ": " + topicFilters.get(i) + "\n" +
                            "Quality of service " + i + ": " + qualityOfServices.get(i) + "\n";
                }
                break;

            case 12:
                message = "Connection type: " + messageType.toString() + "\n" + "Remaining Length: " + remainingLength;
                break;
            case 14:
                message = "Connection type: " + messageType.toString() + "\n" + "Remaining Length: " + remainingLength;
                break;

            case 6:
                message = "Connection type: " + messageType.toString() + "\n" + "Remaing Length: " + remainingLength
                        + "\n" + "PacketID: " + MSB_LSB + "\n";
                break;
            default:
                break;
        }
        return message;
    }

    MessageType messageType;

    public byte[] getMSG_LSB() {
        return MSG_LSB;
    }

    public void setMSG_LSB(byte[] mSG_LSB) {
        MSG_LSB = mSG_LSB;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getRemainingLength() {
        return remainingLength;
    }

    public void setRemainingLength(int remainingLength) {
        this.remainingLength = remainingLength;
    }

    public int getMSB_LSB() {
        return MSB_LSB;
    }

    public void setMSB_LSB(int mSB_LSB) {
        MSB_LSB = mSB_LSB;
    }

    public byte[] getMqtt() {
        return mqtt;
    }

    public void setMqtt(byte[] mqtt) {
        this.mqtt = mqtt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public byte[] getPacketIdentifier() {
        return packetIdentifier;
    }

    public void setPacketIdentifier(byte[] packetIdentifier) {
        this.packetIdentifier = packetIdentifier;
    }

    public byte[] getTopicChars() {
        return topicChars;
    }

    public void setTopicChars(byte[] topicChars) {
        this.topicChars = topicChars;
    }

    public int getTopicLength() {
        return topicLength;
    }

    public void setTopicLength(int topicLength) {
        this.topicLength = topicLength;
    }

    public int getRetain() {
        return Retain;
    }

    public void setRetain(int retain) {
        Retain = retain;
    }

    public int getQoSLevel() {
        return QoSLevel;
    }

    public void setQoSLevel(int qoSLevel) {
        QoSLevel = qoSLevel;
    }

    public int getDUPFlag() {
        return DUPFlag;
    }

    public void setDUPFlag(int dUPFlag) {
        DUPFlag = dUPFlag;
    }

    public ArrayList<String> getTopics() {
        return this.topicFilters;
    }
}
