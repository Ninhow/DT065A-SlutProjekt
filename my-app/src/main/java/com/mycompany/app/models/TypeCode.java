package com.mycompany.app.models;

import java.util.HashMap;
import java.util.Map;

public enum TypeCode {
    CON(0),
    NON(1),
    ACK(2),
    RES(3);

    private Integer codeType;
    private TypeCode(Integer type){
        this.codeType = type;
    }

    public Integer getInteger() {
        return this.codeType;
    }

    private static final Map<Integer, TypeCode> table = new HashMap<>();

    static {
        for( TypeCode env : TypeCode.values()){
            table.put(env.getInteger(), env);
        }
    }

    public static TypeCode CodeName(Integer codeType){
        return table.get(codeType);
    }
}
