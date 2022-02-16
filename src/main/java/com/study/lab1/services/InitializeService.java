package com.study.lab1.services;

import java.util.Map;
public class InitializeService {
    private static final String URL = "jdbc:postgresql://localhost:5432/test_DB";
    private static final String USER_NAME = "test_user";
    private static final String PASSWORD = "12345";

    private final String url;
    private final String userName;
    private final String password;


    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }



    public InitializeService() {
        Map<String, String> getenv = System.getenv();
        String url = getenv.get("URL");
        String userName = getenv.get("USER_NAME");
        String password = getenv.get("PASSWORD");
        this.url = url != null ? url : URL;
        this.userName = userName != null ? userName : USER_NAME;
        this.password = password != null ? password : PASSWORD;
    }
}
