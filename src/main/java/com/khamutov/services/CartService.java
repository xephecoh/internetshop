package com.khamutov.services;

import com.khamutov.entities.CartItem;
import com.khamutov.dao.CartDao;

import java.util.List;

public class CartService {
    private final CartDao cartDao;
    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }
    public void addToCart(CartItem cartItem,String userName){
        cartDao.addToCart(cartItem,userName);
    }
    public List<CartItem> getUserCart(String userName){
        return cartDao.getUserCart(userName);
    }

    public void deleteProductFromCart(String userName,String productName){
        cartDao.deleteFromCart(userName,productName);
    }

}
