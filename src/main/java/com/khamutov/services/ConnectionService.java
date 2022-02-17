package com.khamutov.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private final InitializeService service;

    public ConnectionService() {
        service = new InitializeService();
    }

    public  Connection createConnection()  {
        try {
            return  DriverManager.getConnection(service.getUrl(), service.getUserName(), service.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create connection");
        }
    }

}
