package com.mycompany.app.models;

import java.util.HashMap;
import java.util.Map;

public enum MethodCode {
    EMPTY(0),
    GET(1),
    POST(2),
    PUT(3),
    DELETE(4);

    private Integer methodCode;
    
    private MethodCode(Integer methodCode){
        this.methodCode = methodCode;
    }

    public Integer getInteger() {
        return this.methodCode;
    }

    private static final Map<Integer, MethodCode> table = new HashMap<>();

    static {
        for( MethodCode env : MethodCode.values()){
            table.put(env.getInteger(), env);
        }
    }

    public static MethodCode CodeName(Integer methodType){
        return table.get(methodType);
    }
}
