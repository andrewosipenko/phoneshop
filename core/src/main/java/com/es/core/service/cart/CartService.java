package com.es.core.service.cart;

import com.es.core.model.entity.cart.Cart;

import java.util.Map;

public interface CartService<SessionResource>{

    Cart getCart(SessionResource sessionResource);

    void addPhone(Cart cart, Long phoneId, Long quantity);

    void update(Cart cart, Map<Long, Long> items);

    void remove(Cart cart, Long phoneId);
}
