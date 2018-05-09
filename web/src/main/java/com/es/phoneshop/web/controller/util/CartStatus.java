package com.es.phoneshop.web.controller.util;

import java.math.BigDecimal;

public class CartStatus {
    private long phonesTotal;
    private BigDecimal subtotal;

    public CartStatus(long phonesTotal, BigDecimal subtotal) {
        this.phonesTotal = phonesTotal;
        this.subtotal = subtotal;
    }

    public long getPhonesTotal() {
        return phonesTotal;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
