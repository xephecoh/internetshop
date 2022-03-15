package com.khamutov.jdbc;

import com.khamutov.jdbc.dao.UserDao;
import org.postgresql.ds.PGSimpleDataSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class JdbcUserDao implements UserDao {
    private final PGSimpleDataSource pgSimpleDataSource;
    private final static String GET_USER = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
    private final static String INSERT_USER = "INSERT INTO users (id,user_name,user_password) VALUES (10,?,?)";
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
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
                byte[] resultByteArray = messageDigest.digest();
                StringBuilder stringBuilder = new StringBuilder();
                for (byte b : resultByteArray) {
                    stringBuilder.append(String.format("%02x", b));
                }
                boolean isValid = false;
                statement.setString(1, name);
                statement.setString(2, stringBuilder.toString());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    isValid = true;
                }
                return isValid;

            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable get user from db", e);
        }
        return false;
    }

    @Override
    public void saveUser(String name, String password) {
        try (Connection connection = pgSimpleDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER);
             PreparedStatement statement1 = connection.prepareStatement(GET_USER_BY_NAME)
        ) {
            statement1.setString(1, name);
            int rowsAffected = statement1.executeUpdate();
            if (rowsAffected > 0) {
                throw new RuntimeException("User with name " + name + " already exists");
            }
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : resultByteArray) {
                stringBuilder.append(String.format("%02x", b));
            }
            statement.setString(1, name);
            statement.setString(2, stringBuilder.toString());
            statement.executeQuery();
        } catch (SQLException sqlException) {
            System.out.println("Unable to save user with name " + name);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getUserRole(String name) {
        try (Connection connection = pgSimpleDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_ROLE);
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("No user with name " + name);
    }
}
