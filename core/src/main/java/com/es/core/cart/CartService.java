package com.es.core.cart;

import com.es.core.exception.PhoneNotFoundException;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException;

    /**
     * @param items key: {@link com.es.core.model.phone.Phone#id}
     *              value: quantity
     */
    void update(Map<Long, Long> items) throws PhoneNotFoundException;

    void remove(Long phoneId) throws PhoneNotFoundException;

    BigDecimal getCartCost();

    Long getPhonesCountInCart();
}
