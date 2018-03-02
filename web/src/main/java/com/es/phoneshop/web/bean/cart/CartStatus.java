package com.es.phoneshop.web.bean.cart;

import java.math.BigDecimal;

public class CartStatus {

    private String errorMessage;

    private Long phoneCount;

    private BigDecimal cartCost;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(Long phonesCount) {
        this.phoneCount = phonesCount;
    }

    public BigDecimal getCartCost() {
        return cartCost;
    }

    public void setCartCost(BigDecimal cartCost) {
        this.cartCost = cartCost;
    }
}