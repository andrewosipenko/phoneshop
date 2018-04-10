package com.es.phoneshop.web.bean.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {
    @NotNull
    @Min(value = 1000L)
    private Long phoneId;

    @NotNull
    @Min(value = 1L)
    private Long quantity;

    public CartItem() {
    }

    public CartItem(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
