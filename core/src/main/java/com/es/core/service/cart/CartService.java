package com.es.core.service.cart;

import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.cart.Cart;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity) throws PhoneNotFoundException;

    /**
     * @param items key: {@link com.es.core.model.phone.Phone#id}
     *              value: quantity
     */
    void update(Map<Long, Long> items) throws PhoneNotFoundException;

    void updateOrDelete(Long phoneId, Long quantity);

    void deleteOutOfStock();

    void clearCart();
}
