package com.es.core.order;

import com.es.core.model.PhoneModel;

public class OrderItem {
    private Long id;
    private PhoneModel phone;
    private Order order;
    private Long quantity;

    public PhoneModel getPhone() {
        return phone;
    }

    public void setPhone(final PhoneModel phone) {
        this.phone = phone;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }
}
