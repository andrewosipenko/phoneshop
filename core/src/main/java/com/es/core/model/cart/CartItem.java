package com.es.core.model.cart;

import com.es.core.model.phone.Phone;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {
    @NotNull
    @Min(1)
    private Long quantity;

    private Phone phone;

    public CartItem() {

    }

    public CartItem(@NotNull @Min(1) Long quantity, Phone phone) {
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
