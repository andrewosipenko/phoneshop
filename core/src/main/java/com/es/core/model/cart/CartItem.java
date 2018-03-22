package com.es.core.model.cart;

import com.es.core.model.phone.Phone;

public class CartItem {
    private Phone phone;

    private Long quantity;

    public CartItem() {
    }

    public CartItem(Phone phone, Long quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        if (phone != null ? !phone.equals(cartItem.phone) : cartItem.phone != null) return false;
        return quantity.equals(cartItem.quantity);

    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + quantity.hashCode();
        return result;
    }
}
