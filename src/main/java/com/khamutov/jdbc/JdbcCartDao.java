package com.khamutov.jdbc;


import com.khamutov.entities.Product;
import com.khamutov.jdbc.dao.CartDao;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcCartDao implements CartDao {
    private final RowMapper rowMapper;
    private static final String INSERT_ITEM_TO_CART = "INSERT INTO users_carts VALUES (?,?,?)";
    private static final String GET_USER_CART = "SELECT * FROM users_carts WHERE user_name = ?";
    private static final String DELETE_ITEM_FROM_CART = "DELETE from users_carts WHERE user_name = ? and product_name = ?";
    private final PGSimpleDataSource postgresDataSources;

    public JdbcCartDao(PGSimpleDataSource dataSource) {
        this.rowMapper = new RowMapper();
        this.postgresDataSources = dataSource;
    }

    @Override
    public void addToCart(String userName, String productName, int productPrice) {
        try (Connection connection = postgresDataSources.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ITEM_TO_CART)
        ) {
            statement.setString(1, userName);
            statement.setString(2, productName);
            statement.setInt(3, productPrice);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Unable to save product to cart " + e.getMessage());
        }
    }

    @Override
    public List<Product> getUserCart(String userName) {
        try (Connection connection = postgresDataSources.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_CART)
        ) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            List<Product> cartList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = rowMapper.getCartProduct(resultSet);
                cartList.add(product);
            }
            return cartList;
        } catch (SQLException e) {
            System.out.println("Unable get user cart " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteFromCart(String userName, String itemName) {
        try (Connection connection = postgresDataSources.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_FROM_CART)
        ) {
            statement.setString(1, userName);
            statement.setString(2, itemName);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to delete" + e.getMessage());
        }

    }
}
