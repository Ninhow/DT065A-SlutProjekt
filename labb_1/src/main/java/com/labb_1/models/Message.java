package com.labb_1.models;

import java.util.ArrayList;
import java.util.Map;

public class Message {
    int version;
    TypeCode typeCode;
    int tokenLength;
    ResponseCode responseCode;
    int messageID;
    Map<OptionCode, String> options;
    String payload;

    public Message(){}
    public Message(
            int version, 
            TypeCode typeCode, 
            int tokenLength, 
            ResponseCode responseCode, 
            int messageID, 
            Map<OptionCode, String> options, 
            String payload
        ){

            this.version = version;
            this.typeCode = typeCode;
            this.tokenLength = tokenLength;
            this.responseCode = responseCode;
            this.messageID = messageID;
            this.options = options;
            this.payload = payload;
    }

    @Override
    public String toString() {
        String result = "Version: " + version + "\n" 
                        + "TypeCode: " + typeCode + "\n" 
                        + "Token length: " + tokenLength 
                        + "\n" + "Response Code: " + responseCode + "\n" 
                        + "Message ID: " + messageID + "\n";

        ArrayList<OptionCode> keys = new ArrayList<>(options.keySet());
        for(int k = keys.size() - 1; k >= 0; k--){
            result += "Option Code: " + keys.get(k)+ "\n" + "Value: " + options.get(keys.get(k)) + "\n";
        }
        result +="Payload: " +  payload + "\n";
        return result;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public TypeCode getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(TypeCode typeCode) {
        this.typeCode = typeCode;
    }
    public int getTokenLength() {
        return tokenLength;
    }
    public void setTokenLength(int tokenLength) {
        this.tokenLength = tokenLength;
    }
    public ResponseCode getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
    public int getMessageID() {
        return messageID;
    }
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public Map<OptionCode, String> getOptions() {
        return options;
    }
    public void setOptions(Map<OptionCode, String> options) {
        this.options = options;
    }
    
}
