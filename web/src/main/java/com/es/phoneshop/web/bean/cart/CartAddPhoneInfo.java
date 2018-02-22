package com.es.phoneshop.web.bean.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CartAddPhoneInfo implements Serializable {

    @NotNull
    @Min(1L)
    private Long phoneId;

    @NotNull
    @Min(1L)
    private Long quantity;

    public CartAddPhoneInfo() {
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
