package com.khamutov.services;

import com.khamutov.entities.Product;
import com.khamutov.jdbc.dao.CartDao;

import java.util.List;

public class CartService {
    private final CartDao cartDao;
    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }
    public void addToCart(String userName, String productName, int productPrice){
        cartDao.addToCart(userName,productName,productPrice);
    }
    public List<Product> getUserCart(String userName){
        return cartDao.getUserCart(userName);
    }

    public void deleteProductFromCart(String userName,String productName){
        cartDao.deleteFromCart(productName,userName);
    }

}
