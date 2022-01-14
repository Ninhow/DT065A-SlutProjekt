package com.labb_1.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.labb_1.interfaces.CoapParserInterface;
import com.labb_1.models.Message;
import com.labb_1.models.OptionCode;
import com.labb_1.models.ResponseCode;
import com.labb_1.models.TypeCode;

public class CoapParser implements CoapParserInterface{

    @Override
    public Map<String, String> parseMessage(byte[] message, int length) {
        int tokenLength = message[0] & 0xF;
        Message messageParsedTest = new Message();
        for ( int i = 4 + tokenLength; i < length; i++){
            if(message[i] == -1){
                int optionStart = 4 + tokenLength;
                byte[] messageId = {message[2], message[3]};
                messageParsedTest = new Message(
                    ((message[0] & 0xC0 ) >> 6), 
                    TypeCode.CodeName((message[0] & 0x30) >> 4),
                    (message[0] & 0xF),
                    ResponseCode.CodeName((int)message[1]),
                    new BigInteger(messageId).intValue(),
                    getOptions(Arrays.copyOfRange(message, optionStart, i)),
                    getPayload(Arrays.copyOfRange(message, i, length))
                );
                break;                
            }
            
        }
        System.out.println(messageParsedTest.toString());
        return null;
    }

    public Map<OptionCode, String> getOptions(byte[] arrayOptions){
        int optionDelta = ((arrayOptions[0] & 0xFF) & 0xF0) >> 4;
        int optionLength = (arrayOptions[0] & 0xFF) & 0xF;

        Map<OptionCode, String> options = new HashMap<>();
        for(int i = 0; i < arrayOptions.length;){
            if(i != 0){
                int unsignedDelta = ((arrayOptions[i] & 0xFF) & 0xF0) >> 4;
                optionDelta = optionDelta + unsignedDelta;
                optionLength = (arrayOptions[i] & 0xFF) & 0xF;
                if(optionLength == 0){
                    optionLength = 1;
                }

                options.put(OptionCode.CodeName(optionDelta), getOptionValue(Arrays.copyOfRange(arrayOptions, i + 1, i + optionLength), OptionCode.CodeName(optionDelta)));
                
            }else{
                System.out.println(optionDelta);
                options.put(OptionCode.CodeName(optionDelta), getOptionValue(Arrays.copyOfRange(arrayOptions, i + 1, i + optionLength), OptionCode.CodeName(optionDelta)));
            }
            
            i += optionLength + 1;
            
        }
        return options;
    }

    @Override
    public String getOptionValue(byte[] arrayList, OptionCode optionCode){
        if(OptionCode.CONTENT_FORMAT == optionCode){
            return new BigInteger(1, arrayList).toString();
        }else if(OptionCode.ETAG == optionCode){
            return new String(arrayList);
        }

        return "";
    }

    @Override
    public String getPayload(byte[] arrayOptions){
        return new String(arrayOptions, StandardCharsets.UTF_8);
    };
    
}


