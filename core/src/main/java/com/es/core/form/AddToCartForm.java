package com.es.core.form;

import org.springframework.stereotype.Component;

@Component
public class AddToCartForm {
    private Long phoneId;

    private Long quantity;

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }
}