package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Phone, Long> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public Map<Phone, Long> getItems() {
        return items;
    }
}
