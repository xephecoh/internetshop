package com.khamutov.jdbc;

import com.khamutov.dao.UserDao;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;


public class JdbcUserDao implements UserDao {
    PGSimpleDataSource pgSimpleDataSource;
    final static String GET_USER = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";


    public JdbcUserDao(PGSimpleDataSource pgSimpleDataSource) {
        this.pgSimpleDataSource = pgSimpleDataSource;
    }

    @Override
    public boolean isUserValid(String name, String password) {
        try (Connection connection = pgSimpleDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER);
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
            throw new RuntimeException("Unable get products list", e);
        }
    }
}
