package com.es.phoneshop.web.controller.beans;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

public class QuantityForm {

    private final static String DECIMAL_MIN_VIOLATION_MESSAGE = "Quantity should be >1";
    private final static String DIGITS_VIOLATION_MESSAGE = "Integer expected";

    private Long phoneId;

    @DecimalMin(value = "1", message = DECIMAL_MIN_VIOLATION_MESSAGE)
    @Digits(integer = 3, fraction = 0, message = DIGITS_VIOLATION_MESSAGE)
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
