package com.khamutov.web.security;

import com.khamutov.dao.UserDao;
import com.khamutov.jdbc.JdbcUserDao;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityService {
    private UserDao jdbcUserDao;
    private final static List<String> tokenList = new ArrayList();

    public SecurityService(UserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public Cookie generateCookie() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        Cookie cookie = new Cookie("token", token);
        getTokenList().add(token);
        return cookie;
    }

    public boolean validateUser(String name, String password) {
        return jdbcUserDao.isUserValid(name, password);
    }


    public synchronized List<String> getTokenList() {
        return tokenList;
    }

    public boolean ifTokenPresent(String token) {
        return getTokenList().contains(token);
    }

}
