package com.es.phoneshop.web.model.cart;

import org.hibernate.validator.constraints.Range;

public class CartInfo {

    private Long phoneId;

    @Range(min = 1L)
    private long quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
