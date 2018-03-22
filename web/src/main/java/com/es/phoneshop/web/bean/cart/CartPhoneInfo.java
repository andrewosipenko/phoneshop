package com.es.phoneshop.web.bean.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CartPhoneInfo implements Serializable {

    @NotNull(message = "{quantity.wrongFormat}")
    @Min(value = 1L, message = "{quantity.wrongFormat}")
    private Long phoneId;

    @NotNull(message = "{quantity.wrongFormat}")
    @Min(value = 1L, message = "{quantity.wrongFormat}")
    private Long quantity;

    public CartPhoneInfo() {
    }

    public CartPhoneInfo(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

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
