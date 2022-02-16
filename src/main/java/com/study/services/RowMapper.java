package com.study.lab1.services;




import com.study.lab1.servlets.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowMapper {
    public List<Product> getProductList(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        Product product;
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String string = resultSet.getString(2);
            int price = resultSet.getInt(3);
            product = new Product(id,price,string);
            products.add(product);
        }
        return products;
    }
}
