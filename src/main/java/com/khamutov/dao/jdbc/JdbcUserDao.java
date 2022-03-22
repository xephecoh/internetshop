package com.khamutov.dao.jdbc;

import com.khamutov.dao.UserDao;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;


public class JdbcUserDao implements UserDao {
    private final PGSimpleDataSource pgSimpleDataSource;
    private final static String GET_USER = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
    private final static String INSERT_USER = "INSERT INTO users (user_name,user_password) VALUES (?,?)";
    private final static String GET_USER_BY_NAME = "SELECT * FROM users WHERE user_name = ?";
    private final static String GET_USER_ROLE = "SELECT user_role FROM users WHERE user_name = ?";


    public JdbcUserDao(PGSimpleDataSource pgSimpleDataSource) {
        this.pgSimpleDataSource = pgSimpleDataSource;
    }

    @Override
    public boolean isUserValid(String name, String password) {
        try (Connection connection = pgSimpleDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER)
        ) {
            boolean isValid = false;
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isValid = true;
            }
            return isValid;
        } catch (SQLException e) {
            throw new RuntimeException("Unable get user from db ", e);
        }
    }

    @Override
    public void saveUser(String name, String password) {
        try (Connection connection = pgSimpleDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER);
             PreparedStatement checkUserIfExistsStatement = connection.prepareStatement(GET_USER_BY_NAME)
        ) {
            checkUserIfExistsStatement.setString(1, name);
            ResultSet resultSet = checkUserIfExistsStatement.executeQuery();
            if (resultSet.next()) {
                throw new RuntimeException("User with name " + name + " already exists");
            }
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("Unable to save user with name " + name + sqlException.getMessage());
        }
    }

    @Override
    public String getUserRole(String name) {
        try (Connection connection = pgSimpleDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_ROLE)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userRole = resultSet.getString("user_role");
                if (userRole == null) {
                    throw new RuntimeException("No user with name " + name);
                } else {
                    return userRole;
                }
            }
            return null;
        } catch (SQLException sqlException) {
            throw new RuntimeException("No user with name " + name);
        }
    }
}
