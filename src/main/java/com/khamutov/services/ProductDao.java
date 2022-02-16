package com.khamutov.services;

import com.khamutov.entities.Product;

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

    public void update(int id) {
        try {
            queryExecutor.update(id);
        } catch (SQLException e) {
            throw new RuntimeException("unable to delete",e);
        }
    }

    public void save(int id,String name,int price){

        try {
            queryExecutor.save(id,name,price);
        } catch (SQLException e) {
            throw new RuntimeException("unable to save",e);
        }
    }


}
