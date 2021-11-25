package com.es.core.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items;
    private long totalQuantity;
    private BigDecimal totalCost;

    public Cart() {
        items = new ArrayList<>();
        totalCost = new BigDecimal(0);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
