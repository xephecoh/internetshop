package com.khamutov.jdbc;


import com.khamutov.entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RowMapper {
    public Product getProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        return new Product(id, name, price);
    }
    public Product getCartProduct(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("product_name");
        int price = resultSet.getInt("product_price");
        return new Product(price, name);
    }
}
