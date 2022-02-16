package com.study.lab1.servlets;

import com.study.lab1.services.QueryExecutor;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    private QueryExecutor queryExecutor;

    public ProductDao() {
        this.queryExecutor = new QueryExecutor();
    }

    public List<Product> getAllProducts() {
        try {
            return queryExecutor.getAllProducts();
        } catch (SQLException e) {
           throw new RuntimeException("unable get product list",e);
        }
    }

    public void deleteById(int id) {
        try {
            queryExecutor.deleteItem(id);
        } catch (SQLException e) {
            throw new RuntimeException("unable to delete",e);
        }
    }


}
