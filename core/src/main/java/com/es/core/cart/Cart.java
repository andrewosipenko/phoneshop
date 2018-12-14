package com.es.core.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;
    private BigDecimal cartItemsPrice;
    private Long cartItemsAmount;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getCartItemsPrice() {
        return cartItemsPrice;
    }

    public void setCartItemsPrice(BigDecimal cartItemsPrice) {
        this.cartItemsPrice = cartItemsPrice;
    }

    public Long getCartItemsAmount() {
        return cartItemsAmount;
    }

    public void setCartItemsAmount(Long cartItemsAmount) {
        this.cartItemsAmount = cartItemsAmount;
    }
}
