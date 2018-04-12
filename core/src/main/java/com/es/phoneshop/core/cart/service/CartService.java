package com.es.phoneshop.core.cart.service;

import com.es.phoneshop.core.cart.model.CartItem;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.phone.model.Phone;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartStatus getCartStatus();

    List<CartItem> getCartItems();

    void addPhone(Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void ckeckUpdateItems(Map<Long, Long> updateItems);

    void remove(Long phoneId);
}
