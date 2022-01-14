package com.labb_1.interfaces;

import java.util.Map;

import com.labb_1.models.OptionCode;

public interface CoapParserInterface{
    Map<String, String> parseMessage(byte[] message, int length);
    String getOptionValue(byte[] arrayList, OptionCode optionCode);
    String getPayload(byte[] arrayOptions);
}
