package com.khamutov.dao;

import com.khamutov.entities.Product;

public interface ProductDao {
    Iterable<Product> findAllProducts();
    void delete(int id);
    boolean update(Product product);
    void save(Product product);
}
