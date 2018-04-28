package com.es.phoneshop.core.cart.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "${cart.scope}", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private List<CartRecord> records = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    public List<CartRecord> getRecords() {
        return records;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
