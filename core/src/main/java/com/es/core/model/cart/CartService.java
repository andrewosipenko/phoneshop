package com.es.core.model.cart;

import com.es.core.exception.PhoneNotFindException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    Cart getCart(HttpSession session);

    void recalculateCart(Cart cart);

    void addPhone(Long phoneId, int quantity, Cart cart);

    void update(Map<Long, Long> items, Cart cart);

    void remove(Long phoneId, Cart cart);

    CartItem getCartItem(Long phoneId, Cart cart) throws PhoneNotFindException;

    void clearCart(HttpSession session);
}
