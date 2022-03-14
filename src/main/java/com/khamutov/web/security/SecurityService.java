package com.khamutov.web.security;

import com.khamutov.dao.UserDao;
import com.khamutov.entities.Token;

import javax.servlet.http.Cookie;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SecurityService implements Runnable {
    private final UserDao jdbcUserDao;
    private final static List<Token> tokenList = new ArrayList();

    public SecurityService(UserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public Cookie generateCookie() {
        UUID uuid = UUID.randomUUID();
        String tokenValue = uuid.toString();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Token token = new Token(tokenValue, timestamp.getTime());
        Cookie cookie = new Cookie("token", tokenValue);
        getTokenList().add(token);
        return cookie;
    }


    public boolean validateUser(String name, String password) {
        return jdbcUserDao.isUserValid(name, password);
    }


    public synchronized List<Token> getTokenList() {
        return tokenList;
    }

    public boolean isTokenPresent(String token) {
        Optional<Token> myToken = getTokenList().stream().filter(e -> e.getTokenValue().equals(token)).findAny();
        return myToken.isPresent();
    }

    private boolean recalculateList(Long creationTimestamp) {
        long tokenLifeTime = new Timestamp(System.currentTimeMillis()).getTime() - creationTimestamp;
        return tokenLifeTime > 10;
    }

    @Override
    public void run() {
        System.out.println("From thread" + Thread.currentThread().getName());
        tokenList.stream()
                .filter(e -> recalculateList(e.getCreationTimestamp()))
                .forEach(tokenList::remove);
    }
}
