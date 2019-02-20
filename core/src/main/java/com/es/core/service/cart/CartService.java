package com.es.core.service.cart;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    Cart getCart();
    Cart getCart(HttpSession session);

    void addCartItem(Cart cart, Long phoneId, Long quantity);

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Cart cart, Map<Long, Long> items);

    void update(Cart cart, Long phoneId, Long quantity);

    void remove(Cart cart, Long phoneId);
}
