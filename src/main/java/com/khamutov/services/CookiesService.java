package com.khamutov.services;

import java.util.ArrayList;
import java.util.List;

public class CookiesService {
    private static CookiesService instance;
    private final static List<String> cookiesList = new ArrayList<>();
    public CookiesService() {
    }
    public List<String> getTokenList() {
        return cookiesList;
    }
    public static CookiesService getInstance() {
        if (instance == null) {
            instance = new CookiesService();
        }
        return instance;
    }
}
