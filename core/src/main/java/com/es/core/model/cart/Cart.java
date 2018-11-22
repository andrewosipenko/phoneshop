package com.es.core.model.cart;

import java.util.List;
import java.util.Vector;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new Vector<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
