package com.es.phoneshop.web.controller.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class QuantityForm {

    @DecimalMin(value = "1", message = "should be > 0")
    @NotNull(message = "should be not null")
    private Long quantity;

    public QuantityForm() {
    }

    public QuantityForm(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
