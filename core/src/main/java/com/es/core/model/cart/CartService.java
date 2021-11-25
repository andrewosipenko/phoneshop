package com.es.core.model.cart;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void recalculateCart();

    void addPhone(Long phoneId, int quantity);

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);
}
