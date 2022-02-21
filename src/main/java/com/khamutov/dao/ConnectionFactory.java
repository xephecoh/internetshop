package com.khamutov.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    url,
                    username,
                    password);
        } catch (SQLException e) {
            throw new RuntimeException("Unable get connection to " + url);
        }
    }
}
