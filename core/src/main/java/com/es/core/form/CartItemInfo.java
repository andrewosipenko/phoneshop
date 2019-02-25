package com.es.phoneshop.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartItemInfo {
    @NotNull
    private long phoneId;

    @NotNull
    @Positive
    private long quantity;

    public CartItemInfo() {

    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemInfo{" +
                "phoneId=" + phoneId +
                ", quantity=" + quantity +
                '}';
    }
}
