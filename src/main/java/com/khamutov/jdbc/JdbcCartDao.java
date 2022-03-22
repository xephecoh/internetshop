package com.khamutov.jdbc;


import com.khamutov.entities.CartItem;
import com.khamutov.jdbc.dao.CartDao;
import org.postgresql.ds.PGSimpleDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcCartDao implements CartDao {
    private final ProductRowMapper productRowMapper;
    private static final String INSERT_ITEM_TO_CART = "INSERT INTO users_carts (user_name, product_name, product_quantity)VALUES (?,?,?)";
    private static final String GET_USER_CART = "SELECT * FROM users_carts WHERE user_name = ?";
    private static final String DELETE_ITEM_FROM_CART = "DELETE from users_carts WHERE user_name = ? and product_name = ?";
    private static final String CHECK_IF_ALREADY_INSIDE = "SELECT * from users_carts where user_name = ? and product_name = ?";
    private static final String INSERT = "UPDATE users_carts SET product_quantity = ? WHERE id = ? ";
    private final PGSimpleDataSource postgresDataSources;

    public JdbcCartDao(PGSimpleDataSource dataSource) {
        this.productRowMapper = new ProductRowMapper();
        this.postgresDataSources = dataSource;
    }

    @Override
    public void addToCart(CartItem cartItem, String userName) {
        try (Connection connection = postgresDataSources.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT);
             PreparedStatement checkIfAlreadyInsideStatement = connection.prepareStatement(CHECK_IF_ALREADY_INSIDE);
             PreparedStatement statementIfEmpty = connection.prepareStatement(INSERT_ITEM_TO_CART)
        ) {
            checkIfAlreadyInsideStatement.setString(1, userName);
            checkIfAlreadyInsideStatement.setString(2, cartItem.getProduct().getName());
            ResultSet resultSet = checkIfAlreadyInsideStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quantity = resultSet.getInt("product_quantity");
                quantity = quantity + 1;
                statement.setInt(1, quantity);
                statement.setInt(2, id);
                statement.executeUpdate();
                resultSet.close();
            } else {
                statementIfEmpty.setString(1, userName);
                statementIfEmpty.setString(2, cartItem.getProduct().getName());
                statementIfEmpty.setInt(3, 1);
                statementIfEmpty.executeUpdate();
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Unable to save product to cart " + e.getMessage());
        }
    }

    @Override
    public List<CartItem> getUserCart(String userName) {
        try (Connection connection = postgresDataSources.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_CART)
        ) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            List<CartItem> cartItemList = new ArrayList<>();
            while (resultSet.next()) {
                CartItem cartItem = productRowMapper.getCartItem(resultSet);
                cartItemList.add(cartItem);
            }
            resultSet.close();
            return cartItemList;
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
