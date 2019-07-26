package com.es.phoneshop.web.validator.quantity;

public class ValidatedQuantity {

    @IsValidQuantity
    private String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
