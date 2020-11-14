package com.abc1236.ms.constant.http;

import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

public class HttpMediaType extends MediaType {
    public HttpMediaType(String type) {
        super(type);
    }

    public static final MediaType APPLICATION_JSON_UTF8;
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";


    static {
        APPLICATION_JSON_UTF8 = new MediaType("application", "json", StandardCharsets.UTF_8);
    }
}
