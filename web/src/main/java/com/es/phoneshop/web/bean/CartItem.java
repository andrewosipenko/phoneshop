package com.es.phoneshop.web.bean;


import com.es.core.model.phone.Phone;

import java.math.BigDecimal;

public class CartItem {

    private Phone phone;

    private Long quantity;

    private BigDecimal total;

    public CartItem() {
    }

    public CartItem(Phone phone, Long quantity) {
        this.phone = phone;
        this.quantity = quantity;
        this.total = phone.getPrice().multiply(new BigDecimal(quantity));
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
