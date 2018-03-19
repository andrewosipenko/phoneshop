package com.es.phoneshop.web.model.order;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.Map;

public class Order {
    private Map<Phone, Long> items;

    private BigDecimal subtotal;

    private BigDecimal delivery;

    private BigDecimal total;

    public Map<Phone, Long> getItems() {
        return items;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDelivery() {
        return delivery;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setItems(Map<Phone, Long> items) {
        this.items = items;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setDelivery(BigDecimal delivery) {
        this.delivery = delivery;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
