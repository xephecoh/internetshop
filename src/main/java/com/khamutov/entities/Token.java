package com.khamutov.entities;

public class Token {

    private final String tokenValue;
    private final long creationTimestamp;

    public Token(String tokenValue, long creationTimestamp) {
        this.tokenValue = tokenValue;
        this.creationTimestamp = creationTimestamp;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

}
