package com.mqtt.classes;

public class SequenceGenerator {

    private int currentValue = 0;

    public int getNextSequence() {
        currentValue = currentValue + 1;
        return currentValue;
    }

}
