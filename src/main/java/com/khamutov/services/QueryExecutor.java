package com.khamutov.services;

import com.khamutov.entities.Product;

import java.sql.*;
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
        ) {
            statement.executeUpdate(
                    "DELETE FROM products WHERE id = " + id + ";");
        }

    }


    public void update(int id, String name, int price) throws SQLException {
        try (Connection connection = service.createConnection();

        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE  product SET name = ? price = ? where id = ? ");
            preparedStatement.setInt(3, id);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
        }
    }

    public void save(int id, String name, int price) throws SQLException {
        try (Connection connection = service.createConnection();
        ) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE id = " + id + ";");

            if(resultSet.next()){
                throw new SQLException("Id should be unique");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO products VALUES (?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, price);
            preparedStatement.executeUpdate();
        }
    }
}