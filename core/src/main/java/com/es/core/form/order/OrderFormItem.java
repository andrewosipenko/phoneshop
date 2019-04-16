package com.es.core.form.order;

import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;

@Component
public class OrderFormItem {
    private Phone phone;
    private Long quantity;

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
}
