package com.es.core.cart;

import com.es.core.model.phone.Phone;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    /**
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    private Map<Phone, Long> items;

    private BigDecimal cost;

    public Cart() {
        items = new HashMap<>();
        cost = BigDecimal.ZERO;
    }

    public void addPhone(Phone phone, Long quantity) {
        items.merge(phone, quantity, (oldQuantity, newQuantity) -> oldQuantity + newQuantity);
    }

    public Long getCountItems() {
        return items.values().stream().mapToLong(v -> v).sum();
    }

    public Map<Phone, Long> getItems() {
        return items;
    }

    public void setItems(Map<Phone, Long> items) {
        this.items = items;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
