package com.es.phoneshop.web.controller.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddToCartForm {
    @NotNull
    @Min(1)
    private Long quantity = 0L;
    @NotNull
    private Long phoneId;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }
}
