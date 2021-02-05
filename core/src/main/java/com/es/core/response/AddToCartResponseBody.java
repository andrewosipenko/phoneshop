package com.es.core.response;

import java.math.BigDecimal;

public class AddToCartResponseBody {

    private String msg;

    private String code;

    private BigDecimal totalCost;

    private BigDecimal totalQuantity;

    private BigDecimal quantityToAdd;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setQuantityToAdd(BigDecimal quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
