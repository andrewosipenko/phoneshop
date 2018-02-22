package com.es.phoneshop.web.bean.cart;

import java.math.BigDecimal;

public class CartStatus {

    private String errorMessage;

    private Long phonesCount;

    private BigDecimal cartCost;

    public CartStatus() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getPhonesCount() {
        return phonesCount;
    }

    public void setPhonesCount(Long phonesCount) {
        this.phonesCount = phonesCount;
    }

    public BigDecimal getCartCost() {
        return cartCost;
    }

    public void setCartCost(BigDecimal cartCost) {
        this.cartCost = cartCost;
    }
}