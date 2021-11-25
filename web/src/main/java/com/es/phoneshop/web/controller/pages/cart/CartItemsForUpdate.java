package com.es.phoneshop.web.controller.pages.cart;

import java.util.HashMap;
import java.util.Map;

public class CartItemsForUpdate {

    private Map<Long, String> cartItems = new HashMap<>();


    public CartItemsForUpdate() {

    }

    public CartItemsForUpdate(Map<Long, String> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<Long, String> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, String> cartItems) {
        this.cartItems = cartItems;
    }
}
