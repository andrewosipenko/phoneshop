package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.phone.model.Phone;

public class CartItem {
    private final Phone phone;
    private Long quantity;

    CartItem(Phone phone, Long quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
