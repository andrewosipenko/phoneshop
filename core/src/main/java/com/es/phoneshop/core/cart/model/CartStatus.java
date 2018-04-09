package com.es.phoneshop.core.cart.model;

import java.math.BigDecimal;

public class CartStatus {
    private Long phonesTotal;
    private BigDecimal costTotal;

    public CartStatus(Long phonesTotal, BigDecimal costTotal) {
        this.phonesTotal = phonesTotal;
        this.costTotal = costTotal;
    }

    public Long getPhonesTotal() {
        return phonesTotal;
    }

    public void setPhonesTotal(Long phonesTotal) {
        this.phonesTotal = phonesTotal;
    }

    public BigDecimal getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }
}
