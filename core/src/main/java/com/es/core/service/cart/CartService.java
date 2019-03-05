package com.es.core.service.cart;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface CartService {

    Cart getCart();

    void addCartItem(Cart cart, Long phoneId, Long quantity);

    void update(Cart cart, Map<Long, Long> items);

    void remove(Cart cart, Long phoneId);

    Map<Long, Long> createMapForUpdating(Long[] quantities, List<CartItem> cartItems);
}
