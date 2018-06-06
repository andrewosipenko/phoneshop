package com.es.phoneshop.web.controller.beans;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

public class QuantityForm {

    private Long phoneId;

    @DecimalMin(value = "1", message = "{quantityForm.quantity.positiveExpected}")
    @Digits(integer = 3, fraction = 0, message = "{quantityForm.quantity.integerExpected}")
    private String quantity;


    public Long getPhoneId() {
        return phoneId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
