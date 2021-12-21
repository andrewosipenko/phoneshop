package com.es.phoneshop.web.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddCartRequest {
    @NotNull(message = "phoneId is null")
    private Long phoneId;

    @NotNull(message = "Please, insert quantity")
    @Min(value = 1, message = "quantity should be > 0")
    private Integer quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
