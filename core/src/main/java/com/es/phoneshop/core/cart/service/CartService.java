package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.CartRecord;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartStatus getStatus();

    List<CartRecord> getRecords();

    void add(Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);
}
