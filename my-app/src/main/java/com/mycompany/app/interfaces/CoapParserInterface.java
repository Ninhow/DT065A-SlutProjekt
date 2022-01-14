package com.mycompany.app.interfaces;

import java.util.Map;

import com.mycompany.app.models.Message;
import com.mycompany.app.models.OptionCode;

public interface CoapParserInterface{
    Message parseMessage(byte[] message, int length);
    String getOptionValue(byte[] arrayList, OptionCode optionCode);
    String getPayload(byte[] arrayOptions);
}
