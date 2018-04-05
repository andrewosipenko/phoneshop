package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;

public class CartEntry {

    private Phone phone;

    private Long quantity;

    public CartEntry(Phone phone, Long quantity){
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

    public BigDecimal obtainCost(){
        return phone.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
