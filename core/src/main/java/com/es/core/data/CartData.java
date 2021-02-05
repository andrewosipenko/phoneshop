package com.es.core.data;

import com.es.core.model.CartItemModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartData {

    private List<CartItemModel> cartItems;

    private Long totalQuantity;

    private BigDecimal totalCost;

    public CartData() {
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public List<CartItemModel> getCartItems() {
        return cartItems;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setCartItems(List<CartItemModel> cartItems) {
        this.cartItems = cartItems;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
