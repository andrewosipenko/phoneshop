package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.throwable.OutOfStockException;
import com.es.phoneshop.core.phone.model.Phone;

import java.util.Map;

public interface CartService {
    Cart getCart();

    void add(Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);

    void clear();

    void validateStocksAndRemoveOdd() throws OutOfStockException;
}
