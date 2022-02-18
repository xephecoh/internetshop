package com.khamutov.services;



import java.util.HashMap;
import java.util.Map;

public class Properties {
    private static Map<String, String> properties = new HashMap<>();


    static {

        properties.put("script","src/main/resources/static");
        Properties.class.getResource("index.js");
    }

    public static String getProperty(String key) {
        return properties.get(key);
    }
}