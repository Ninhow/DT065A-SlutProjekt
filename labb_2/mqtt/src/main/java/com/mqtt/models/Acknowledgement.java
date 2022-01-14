package com.mqtt.models;

public class Acknowledgement {
    private byte[] byteArray;

    public Acknowledgement(int messageType) {
        if (messageType == 1) { // Connect acknowledgement
            this.byteArray = new byte[] { (byte) 0b00100000, (byte) 0b00000010, (byte) 0x0, (byte) 0x0 };
        } else if (messageType == 12) { // Ping response
            this.byteArray = new byte[] { (byte) 0b11010000, (byte) 0x0 };
        }
    }

    public Acknowledgement(MessageType messageType, byte[] packetID) {
        if (messageType == MessageType.SUBSCRIBE) { // Subscribe acknowledgement
            this.byteArray = new byte[] { (byte) 0b10010000, (byte) 0b00000011, (byte) packetID[0], (byte) packetID[1],
                    0x0 };
        } else if (messageType == MessageType.UNSUBACK) { // Unsubscribe acknowledgement
            this.byteArray = new byte[] { (byte) 0b10110000, (byte) 0b00000010, (byte) packetID[0],
                    (byte) packetID[1] };
        }
    }

    public byte[] getByteArray() {
        return this.byteArray;
    }
}
