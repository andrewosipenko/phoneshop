package com.es.phoneshop.web.bean.cart;

import java.math.BigDecimal;

public class CartInfo {
    private Long itemsCount;

    private BigDecimal cost;

    public CartInfo() {
    }

    public CartInfo(Long itemCount, BigDecimal cost) {
        this.itemsCount = itemCount;
        this.cost = cost;
    }

    public Long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Long itemsCount) {
        this.itemsCount = itemsCount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
