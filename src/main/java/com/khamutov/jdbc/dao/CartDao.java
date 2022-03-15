package com.khamutov.jdbc.dao;

import com.khamutov.entities.Product;

import java.util.List;

public interface CartDao {
    void addToCart(String userName, String productName, int productPrice);
    List<Product>getUserCart(String userName);
    void deleteFromCart(String itemName,String userName);
}
