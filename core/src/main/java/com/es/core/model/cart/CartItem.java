package com.es.core.model.cart;

import com.es.core.model.phone.Phone;

public class CartItem {
    private Long quantity;

    private Phone phone;

    public CartItem() {

    }

    public CartItem(Long quantity, Phone phone) {
        this.quantity = quantity;
        this.phone = phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "quantity=" + quantity +
                ", phone=" + phone +
                '}';
    }
}
