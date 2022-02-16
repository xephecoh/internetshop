package com.khamutov.services;




import com.khamutov.main.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowMapper {
    public List<Main.Product> getProductList(ResultSet resultSet) throws SQLException {
        List<Main.Product> products = new ArrayList<>();
        Main.Product product;
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String string = resultSet.getString(2);
            int price = resultSet.getInt(3);
            product = new Main.Product(id,price,string);
            products.add(product);
        }
        return products;
    }
}
