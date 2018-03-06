package com.es.phoneshop.web.model.cart;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class CartPhone {
    private Long phoneId = 0L;

    @NotNull(message = "{quantity.empty}")
    @Range(min = 1L, message = "{quantity.wrongFormat}")
    private Long quantity = 0L;

    public CartPhone() {
    }

    public CartPhone(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

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
