package com.khamutov.entities;

public class Token {

    private String tokenValue;
    private long creationTimestamp;

    public Token(String tokenValue, long creationTimestamp) {
        this.tokenValue = tokenValue;
        this.creationTimestamp = creationTimestamp;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
