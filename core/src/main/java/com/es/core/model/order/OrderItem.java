package com.es.core.model.order;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private Phone phone;
    private Order order;
    private Long quantity;
    private BigDecimal total;

    public OrderItem() {

    }

    public OrderItem(Phone phone, Order order, Long quantity, BigDecimal total) {
        this(null, phone, order, quantity, total);
    }

    public OrderItem(Long id, Phone phone, Order order, Long quantity, BigDecimal total) {
        this.id = id;
        this.phone = phone;
        this.order = order;
        this.quantity = quantity;
        this.total = total;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
