package com.es.core.service.cart;

import com.es.core.model.entity.cart.Cart;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    Cart getCart(HttpSession httpSession);

    void addPhone(Cart cart, Long phoneId, Long quantity);

    Map<Long, String> update(Cart cart, Map<Long, Long> items, Map<Long, String> errors);

    void remove(Cart cart, Long phoneId);

    Map<Long, String> trimRedundantProducts(Cart cart);
}
