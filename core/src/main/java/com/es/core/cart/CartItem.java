package com.es.core.cart;

import java.util.Objects;

public class CartItem {
    private Long phoneId;
    private Long phoneQuantity;

    public CartItem() {
    }

    public CartItem(Long phoneId, Long phoneQuantity) {
        this.phoneId = phoneId;
        this.phoneQuantity = phoneQuantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getPhoneQuantity() {
        return phoneQuantity;
    }

    public void setPhoneQuantity(Long phoneQuantity) {
        this.phoneQuantity = phoneQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(phoneId, cartItem.phoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneId);
    }
}
