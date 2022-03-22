package com.khamutov.dao;

import com.khamutov.entities.CartItem;
import java.util.List;

public interface CartDao {
    void addToCart(CartItem cartItem, String userName);

    List<CartItem> getUserCart(String userName);

    void deleteFromCart(String itemName, String userName);
}
