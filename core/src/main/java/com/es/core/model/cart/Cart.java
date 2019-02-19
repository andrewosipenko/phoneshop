package com.es.core.model.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal totalPrice;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
