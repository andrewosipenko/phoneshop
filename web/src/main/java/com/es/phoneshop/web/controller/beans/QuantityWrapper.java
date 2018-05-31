package com.es.phoneshop.web.controller.beans;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

public class QuantityWrapper{

    private final static String CONSTRAINT_VIOLATION_MESSAGE = "Wrong format";

    @DecimalMin(value = "1", message = CONSTRAINT_VIOLATION_MESSAGE)
    @Digits(integer = 3, fraction = 0, message = CONSTRAINT_VIOLATION_MESSAGE)
    private String quantity;

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public Long getValue(){
        return Long.valueOf(quantity);
    }

    @Override
    public String toString() {
        return quantity;
    }
}
