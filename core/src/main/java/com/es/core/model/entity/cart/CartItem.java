package com.es.core.model.entity.cart;

import com.es.core.model.entity.phone.Phone;

import java.io.Serializable;

public class CartItem implements Serializable, Cloneable{
    private final Phone phone;
    private Long quantity;

    public CartItem(Phone phone, Long quantity) {
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

    @Override
    public String toString() {
        return phone.getBrand() + " " + phone.getModel() + " : " + quantity ;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}