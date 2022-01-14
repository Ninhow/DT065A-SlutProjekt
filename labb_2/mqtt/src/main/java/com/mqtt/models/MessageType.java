package com.mqtt.models;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {

    RESERVED(0),
    CONNECT(1),
    CONNACK(2),
    PUBLISH(3),
    PUBACK(4),
    PUBREC(5),
    PUBREL(6),
    PUBCOMP(7),
    SUBSCRIBE(8),
    SUBACK(9),
    UNSUBSCRIBE(10),
    UNSUBACK(11),
    PINGREQ(12),
    PINGRESP(13),
    DISCONNECT(14),
    RESERVED_15(15);

    private Integer messageType;

    private MessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getInteger() {
        return this.messageType;
    }

    private static final Map<Integer, MessageType> table = new HashMap<>();

    static {
        for (MessageType env : MessageType.values()) {
            table.put(env.getInteger(), env);
        }
    }

    public static MessageType CodeName(Integer methodType) {
        return table.get(methodType);
    }
}
