package com.es.core.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AddingToCartForm {

    private Long totalQuantity;

    private BigDecimal totalCost;

    @NotNull
    @Min(value = 1L)
    private Long quantityToAdd;

    private Long productId;

    public AddingToCartForm() {
    }

    public AddingToCartForm(Long quantityToAdd, Long productId) {
        this.quantityToAdd = quantityToAdd;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setQuantityToAdd(Long quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
