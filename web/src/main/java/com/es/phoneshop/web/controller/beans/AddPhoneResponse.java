package com.es.phoneshop.web.controller.beans;

import java.math.BigDecimal;

public class AddPhoneResponse {

    private BigDecimal cartSubTotal;

    private Long cartQuantity;

    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
