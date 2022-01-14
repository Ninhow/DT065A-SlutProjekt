package com.labb_1.models;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
    
    EMPTY(0), //Send methods
    GET(1),
    POST(2),
    PUT(3),
    DELETE(4),
    FETCH(5),
    PATCH(6),
    IPATCH(7),

    CREATED(65),  //Success
    DELETED(66),  
    VALID(67),    
    CHANGED(68),  
    CONTENT(69),  
    CONTINUE(95), 
    BAD_REQUEST(128),

    UNAUTHORIZED(129),  //Client ERROR
    BAD_OPTION(130),    
    FORBIDDEN(131),     
    NOT_FOUND(132),     
    NOT_ALLOWED(133),   
    NOT_ACCEPTABLE(134),
    REQUEST_ENTITY_INCOMPLETE(136),
    PREDCONDITION_FAILED(140),
    REQUEST_LARGE(141),
    UNSUPPORTED_CONTENT(143),

    INTERNAL_SERVER_ERROR(160), //Server error
    NOT_IMPLEMENTED(161),
    BAD_GATEWAY(162),
    SERVICE_UNAVAILABLE(163),
    GATEWAY_TIMEOUT(164),
    PROXY_NOT_SUPPORTED(165);
    
    private Integer responseCode;
    private ResponseCode(Integer responseCode){
        this.responseCode = responseCode;
    }

    public Integer getInteger() {
        return this.responseCode;
    }

    private static final Map<Integer, ResponseCode> table = new HashMap<>();

    static {
        for( ResponseCode env : ResponseCode.values()){
            table.put(env.getInteger(), env);
        }
    }

    public static ResponseCode CodeName(Integer codeType){
        return table.get(codeType);
    }

}
