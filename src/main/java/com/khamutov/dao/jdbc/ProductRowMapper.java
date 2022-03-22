package com.khamutov.dao.jdbc;


import com.khamutov.entities.CartItem;
import com.khamutov.entities.Product;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductRowMapper {
    public Product getProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        return new Product(id, name, price);
    }

    public CartItem getCartItem(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("product_name");
        int productQuantity = resultSet.getInt("product_quantity");
        return new CartItem(new Product(name), productQuantity);
    }
}
