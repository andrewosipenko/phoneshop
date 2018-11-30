package com.es.core.services.cart;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.cart.Cart;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {
    Cart getCart();

    void addPhone(Long phoneId, Integer quantity) throws OutOfStockException;

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Map<Long, Integer> items);

    void remove(Long phoneId);

    Integer getQuantityOfProducts();

    BigDecimal getTotalPriceOfProducts();
}
