package com.es.core.service.cart;


import com.es.core.model.cartItem.CartItem;

import java.util.Map;

public interface CartService {

    void addPhone(Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);

}
