package com.es.phoneshop.web.controller.pages.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddCartForm {
    @NotNull(message = "phoneIs is null")
    private Long phoneId;

    @NotNull
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
