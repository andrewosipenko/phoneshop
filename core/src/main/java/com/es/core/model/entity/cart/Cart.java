package com.es.core.model.entity.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Cart implements Serializable, Cloneable {
    private Long totalQuantity;
    private final Currency currency = Currency.getInstance(Locale.US);

    private BigDecimal totalCost;

    private List<CartItem> cartItems;

    public Cart() {
        this.totalQuantity = 0L;
        this.totalCost = new BigDecimal(0);
        this.cartItems = new ArrayList<>();
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getItems() {
        return this.cartItems;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return cartItems.toString();
    }

    public Currency getCurrency() {
        return currency;
    }
}
