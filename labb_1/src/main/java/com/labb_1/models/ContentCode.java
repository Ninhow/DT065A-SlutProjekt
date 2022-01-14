package com.labb_1.models;

import java.util.HashMap;
import java.util.Map;

import com.labb_1.interfaces.CodeInterface;

public enum ContentCode implements CodeInterface<ContentCode>{
    TEXT(0),
    APP_LINK(40),
    APP_XML(41),
    APP_OCT(42),
    APP_EXI(47),
    APP_JSON(50),
    APP_CBOR(60);

    private Integer contentCode;
    
    private ContentCode(Integer methodCode){
        this.contentCode = methodCode;
    }

    public Integer getInteger() {
        return this.contentCode;
    }

    private static final Map<Integer, ContentCode> table = new HashMap<>();

    static {
        for( ContentCode env : ContentCode.values()){
            table.put(env.getInteger(), env);
        }
    }

    @Override
    public ContentCode CodeName(Integer methodType) {
        return table.get(methodType);
    }


}
