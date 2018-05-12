package com.es.core.form.order;

import com.es.core.model.phone.Phone;

public class OrderFormItem {
    private Phone phone;
    private Long quantity;

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}
