package com.study.lab1.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private final InitializeService service;

    public ConnectionService() {
        service = new InitializeService();
    }

    public  Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(service.getUrl(), service.getUserName(), service.getPassword());
    }

}
