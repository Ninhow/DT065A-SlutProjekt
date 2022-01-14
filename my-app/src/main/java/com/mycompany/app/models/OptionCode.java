package com.mycompany.app.models;

import java.util.HashMap;
import java.util.Map;

public enum OptionCode {
    IF_MATCH(1),
    URI_HOST(3),
    ETAG(4),
    IF_NONE_MATCH(5),
    URI_PORT(7),
    LOCATION_PATH(8),
    URI_PATH(11),
    CONTENT_FORMAT(12),
    MAX_AGE(14),
    URI_QUERY(15),
    ACCEPT(17),
    LOCATION_QUERY(20),
    SIZE2(28),
    PROXY_URI(35),
    PROXY_SCHEME(39),
    SIZE1(60);

    private Integer optionCode;
    
    private OptionCode(Integer optionCode){
        this.optionCode = optionCode;
    }

    public Integer getInteger() {
        return this.optionCode;
    }

    private static final Map<Integer, OptionCode> table = new HashMap<>();

    static {
        for( OptionCode env : OptionCode.values()){
            table.put(env.getInteger(), env);
        }
    }

    public static OptionCode CodeName(Integer methodType){
        return table.get(methodType);
    }
}
