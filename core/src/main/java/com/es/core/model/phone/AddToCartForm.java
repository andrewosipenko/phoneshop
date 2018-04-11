package com.es.core.model.phone;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Component
public class AddToCartForm {
    @NotNull
    private Long phoneId;

    @Min(value = 1L, message = "Quantity should be integer positive number")
    @NotNull
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