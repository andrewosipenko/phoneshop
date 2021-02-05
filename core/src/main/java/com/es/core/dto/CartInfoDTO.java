package com.es.core.dto;

import com.es.core.dto.validation.ValidQuantityAndStock;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ValidQuantityAndStock
public class CartInfoDTO {

    private String quantityToAdd;

    @NotNull
    @Min(value = 1000L)
    private Long productId;

    public CartInfoDTO() {
    }

    public CartInfoDTO(Long productId, String quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setQuantityToAdd(String quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
