package com.es.core.cart;

import com.es.core.model.cart.Cart;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void recalculateCart();

    void addPhone(Long phoneId, Integer quantity);

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);
}
