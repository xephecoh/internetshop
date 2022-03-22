package com.khamutov.entities;

public enum UserRole {
    GUEST(0),USER(1),ADMIN(2);
    private final int authority;

    UserRole(int authority) {
        this.authority = authority;
    }

    public int getAuthority() {
        return authority;
    }

}
