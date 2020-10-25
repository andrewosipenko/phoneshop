package com.es.core.service.cart;

import com.es.core.model.entity.phone.Phone;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link Phone#getId()}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);
}
