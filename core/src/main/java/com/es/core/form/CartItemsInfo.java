package com.es.core.form;

import java.util.Arrays;

public class CartItemsInfo {
    private Long[] quantity;

    public CartItemsInfo() {

    }

    public CartItemsInfo(Long[] quantity) {
        this.quantity = quantity;
    }

    public Long[] getQuantity() {
        return quantity;
    }

    public void setQuantity(Long[] quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemsInfo{" +
                "quantity=" + Arrays.toString(quantity) +
                '}';
    }
}
