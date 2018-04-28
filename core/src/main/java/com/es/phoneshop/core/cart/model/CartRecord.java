package com.es.phoneshop.core.cart.model;

import com.es.phoneshop.core.phone.model.Phone;

public class CartRecord {
    private Phone phone;
    private Long quantity;

    public CartRecord(Phone phone, Long quantity) {
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
