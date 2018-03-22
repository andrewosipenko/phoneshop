package com.es.core.model.order;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private Phone phone;
    private Order order;
    private Long quantity;
    private BigDecimal total = BigDecimal.ZERO;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        return id != null ? id.equals(orderItem.id) : orderItem.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
