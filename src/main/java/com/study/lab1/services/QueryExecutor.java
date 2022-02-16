package com.study.lab1.services;

import com.study.lab1.servlets.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class QueryExecutor {

    private final ConnectionService service;
    private final RowMapper mapper;

    public QueryExecutor() {
        this.mapper = new RowMapper();
        service = new ConnectionService();

    }

    public List<Product> getAllProducts() throws SQLException {
        try (Connection connection = service.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from products")
        ) {
            return mapper.getProductList(resultSet);
        }
    }
    public void deleteItem(int id) throws SQLException {
        try (Connection connection = service.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("DELETE FROM products WHERE id=" + id);
        ) {}

    }


}
