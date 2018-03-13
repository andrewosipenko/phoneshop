package com.es.core.model.order;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private Phone phone;
    private Order order;
    private Long quantity;

    public OrderItem() {
    }

    public OrderItem(Phone phone, Order order, Long quantity) {
        this(null, phone, order, quantity);
    }

    public OrderItem(Long id, Phone phone, Order order, Long quantity) {
        this.id = id;
        this.phone = phone;
        this.order = order;
        this.quantity = quantity;
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
        if(phone.getPrice() == null){
            return BigDecimal.ZERO;
        }
        return phone.getPrice().multiply(new BigDecimal(quantity));
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
