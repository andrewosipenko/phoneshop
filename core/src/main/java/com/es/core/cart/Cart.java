package com.es.core.cart;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void removeItem(Long phoneId, String color) {
        Phone phone = items.keySet().stream()
                .filter(phone1 -> phone1.getId().equals(phoneId))
                .filter(phone1 -> phone1.getColors().toArray(new Color[]{})[0].getCode().equals(color))
                .distinct()
                .collect(Collectors.toList())
                .get(0);

        items.put(phone, items.get(phone) - 1);
    }

    public Long countItems() {
        return items.values().stream().reduce(0L, (v1, v2) -> v1 + v2);
    }
}
