package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Phone, Long> items = new HashMap<>();

    public Cart() { }

    public Map<Phone, Long> getItems() {
        return items;
    }

    public BigDecimal getPrice() {
        return items.keySet().stream()
                .map(phone -> phone.getPrice().multiply(BigDecimal.valueOf(items.get(phone))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(Phone phone, Long quantity) {
        items.merge(phone, quantity, (a, b) -> a + b);
    }

    public void removeItem(Phone phone) {
        items.put(phone, items.get(phone) - 1);
    }

    public Long countItems() {
        return items.values().stream().reduce(0L, (v1, v2) -> v1 + v2);
    }
}
