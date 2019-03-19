package com.es.phoneshop.web.form;

import java.util.Arrays;

public class CartItemsInfo {
    private String[] quantity;

    public CartItemsInfo() {
    }

    public CartItemsInfo(String[] quantity) {
        this.quantity = quantity;
    }

    public String[] getQuantity() {
        return quantity;
    }

    public void setQuantity(String[] quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemsInfo{" +
                "quantity=" + Arrays.toString(quantity) +
                '}';
    }
}
