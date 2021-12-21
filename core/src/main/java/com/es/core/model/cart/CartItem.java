package com.es.core.model.cart;

import com.es.core.model.phone.Phone;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Phone phone;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Phone phone, Integer quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(Integer additionalQuantity) {
        this.quantity = this.quantity + additionalQuantity;
    }

    @Override
    public String toString() {
        return phone.toString() + "-" + quantity;
    }
}
