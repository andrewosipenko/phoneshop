package com.es.core.model.cart;

import java.util.Objects;

public class CartItem {
    private Long phoneId;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Long phoneId, Integer quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(phoneId, cartItem.phoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneId);
    }
}
