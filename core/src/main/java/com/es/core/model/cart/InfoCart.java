package com.es.core.model.cart;

import java.math.BigDecimal;

public class InfoCart {

    private Long totalCount;
    private BigDecimal subtotalPrice;

    public InfoCart() {

    }

    public InfoCart(Long totalCount, BigDecimal subtotalPrice) {
        this.totalCount = totalCount;
        this.subtotalPrice = subtotalPrice;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }
}
