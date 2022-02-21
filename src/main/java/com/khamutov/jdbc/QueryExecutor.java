package com.khamutov.jdbc;

import com.khamutov.dao.ConnectionFactory;
import com.khamutov.entities.Product;
import com.khamutov.jdbc.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QueryExecutor {
    private static final String ALL_PRODUCT_QUERY = "SELECT * FROM products";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE  products SET name = ? ,price = ? where id = ? ";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM products WHERE id = ?;";
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO products VALUES (?,?,?)";
    private final ConnectionFactory connectionFactory;
    private final RowMapper mapper;


    public QueryExecutor(ConnectionFactory connectionFactory) {
        this.mapper = new RowMapper();
        this.connectionFactory = connectionFactory;
    }

    public List<Product> getAllProducts() {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ALL_PRODUCT_QUERY)
        ) {
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = mapper.getProduct(resultSet);
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException("Unable get products list", e);
        }
    }

    public void deleteItem(int id) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("unable to delete", e);
        }
    }

    public boolean update(Product product) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     UPDATE_PRODUCT_QUERY);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            int i = preparedStatement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update", e);
        }
    }

    public void save(Product product) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_PRODUCT_QUERY);
        ) {
            statement.setInt(1, product.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                throw new SQLException("Product with same id already created");
            }
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("unable to save" + e);
        }
    }
}
