package com.es.phoneshop.web.bean.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.es.phoneshop.web.controller.constants.AjaxConstants.WRONG_INPUT;

public class CartItem {
    @NotNull(message = WRONG_INPUT)
    @Min(value = 1000L, message = WRONG_INPUT)
    private Long phoneId;

    @NotNull(message = WRONG_INPUT)
    @Min(value = 1L, message = WRONG_INPUT)
    private Long quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
