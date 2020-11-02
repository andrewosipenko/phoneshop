package com.es.phoneshop.web.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MiniCartDTO implements Serializable {

    private Long totalQuantity;

    private BigDecimal totalCost;

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
