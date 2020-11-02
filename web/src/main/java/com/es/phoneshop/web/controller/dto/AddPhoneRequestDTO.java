package com.es.phoneshop.web.controller.dto;

import java.io.Serializable;

public class AddPhoneRequestDTO implements Serializable {

    private Long phoneId;

    private String quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddPhoneDTO{" +
                "phoneId=" + phoneId +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
