package ru.nikkitavr.geotagger.geoservice.utils;

import java.nio.charset.StandardCharsets;

public class RedisStringBuilder {

    public static String buildString(String str){
        return new String(str.getBytes(), StandardCharsets.UTF_8);
    }

    public static String buildString(Long value){
        return new String(Long.toString(value).getBytes(), StandardCharsets.UTF_8);
    }
}
