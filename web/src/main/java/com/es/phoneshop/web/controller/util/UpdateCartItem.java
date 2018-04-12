package com.es.phoneshop.web.controller.util;

import com.es.phoneshop.core.cart.model.CartItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateCartItem {
    @NotNull
    @Min(1)
    private Long quantity;
    @NotNull
    private Long phoneId;

    public static UpdateCartItem fromCartItem(CartItem item) {
        UpdateCartItem updateItem = new UpdateCartItem();
        updateItem.setPhoneId(item.getPhone().getId());
        updateItem.setQuantity(item.getQuantity());
        return updateItem;
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
