package com.es.phoneshop.web.model.cart;


import java.math.BigDecimal;

public class CartStatus {

    private long countItems;

    private BigDecimal price = BigDecimal.ZERO;

    private String errorMessage = "";

    public long getCountItems() {
        return countItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setCountItems(long countItems) {
        this.countItems = countItems;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}