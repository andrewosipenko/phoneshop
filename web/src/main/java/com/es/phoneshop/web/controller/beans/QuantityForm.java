package com.es.phoneshop.web.controller.beans;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class QuantityForm {

    private Long phoneId;

    @DecimalMin(value = "1", message = "{quantityForm.quantity.positiveExpected}")
    @NotNull(message = "{quantityForm.quantity.integerExpected}")
    private Long quantity;


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
