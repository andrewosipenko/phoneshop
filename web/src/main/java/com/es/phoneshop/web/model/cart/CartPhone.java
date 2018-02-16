package com.es.phoneshop.web.model.cart;

import org.hibernate.validator.constraints.Range;

public class CartPhone {

    private Long phoneId;

    @Range(min = 1L)
    private long quantity;

    private String color;

    public Long getPhoneId() {
        return phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getColor() {
        return color;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
