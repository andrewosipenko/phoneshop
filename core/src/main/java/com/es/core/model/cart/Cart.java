package com.es.core.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal totalPrice = new BigDecimal(0);

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(cartItems, cart.cartItems) &&
                Objects.equals(totalPrice, cart.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems, totalPrice);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
