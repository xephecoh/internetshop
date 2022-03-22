package com.khamutov.entities;

import java.time.LocalDateTime;

public class Session {

    private final String tokenValue;
    private final LocalDateTime creationDateTime;
    private final UserRole userRole;
    private final String userName;

    public Session(String tokenValue, LocalDateTime creationDateTime, UserRole userRole, String userName) {
        this.tokenValue = tokenValue;
        this.creationDateTime = creationDateTime;
        this.userRole = userRole;
        this.userName = userName;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationDateTime;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getUserName() {
        return userName;
    }
}
