package com.es.core.service.cart;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    Cart getCart();

    void addCartItem(Cart cart, Long phoneId, Long quantity);

    void update(Cart cart, Map<Long, Long> items);

    void update(Cart cart, Long phoneId, Long quantity);

    void remove(Cart cart, Long phoneId);
}
