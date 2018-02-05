package com.es.core.cart;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Long, Long> items = new HashMap<>();

    public Cart() { }

    public void addItem(Long id, Long quantity) {
        items.merge(id, quantity, (a, b) -> a + b);
    }

    public Long countItems() {
        return items.values().stream().reduce((v1, v2) -> v1 + v2).orElse(0L);
    }

}
