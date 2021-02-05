package com.es.core.model;

import java.util.Objects;

public class CartItemModel {

    private PhoneModel phone;

    private Long quantity;

    public CartItemModel(Long quantity, PhoneModel phone) {
        this.quantity = quantity;
        this.phone = phone;
    }

    public PhoneModel getPhone() {
        return phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setPhone(PhoneModel phone) {
        this.phone = phone;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemModel cartItem = (CartItemModel) o;
        return phone.equals(cartItem.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
