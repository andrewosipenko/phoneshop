package com.es.core.service.cart;

import com.es.core.model.entity.cart.Cart;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    Cart getCart(HttpSession httpSession);

    void addPhone(Cart cart, Long phoneId, Long quantity);

    void update(Cart cart, Map<Long, Long> items);

    void remove(Cart cart, Long phoneId);
}
