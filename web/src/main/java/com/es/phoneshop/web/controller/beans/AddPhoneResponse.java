package com.es.phoneshop.web.controller.beans;

import java.math.BigDecimal;
import java.util.List;

public class AddPhoneResponse {

    private BigDecimal cartSubTotal;

    private Long cartQuantity;

    private Boolean valid;

    private List<String> errors;

    public BigDecimal getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(BigDecimal cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public Long getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Long cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
