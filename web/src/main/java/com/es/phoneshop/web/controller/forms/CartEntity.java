package com.es.phoneshop.web.controller.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartEntity {
    @NotNull(message="You have to enter something")
    @Min(value = 1, message = "Quantity has to be more than one")
    @Max(value = 20, message = "Quantity has to be less than twenty")
    private Long quantity;

    private Long phoneId;

    public Long getQuantity() {
        if(quantity == null) {
            return 0L;
        } else {
            return quantity;
        }
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
