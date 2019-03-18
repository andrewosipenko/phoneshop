package com.es.core.service.cart;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;

import java.util.List;
import java.util.Map;

public interface CartService {

    Cart getCart();

    void addCartItem(Cart cart, Long phoneId, Long quantity);

    void update(Cart cart, Map<Long, Long> items);

    void remove(Cart cart, Long phoneId);

    Map<Long, Long> createMapForUpdating(String[] quantities, List<CartItem> cartItems);

    void clearCart(Cart cart);

    void removeMissingQuantity(CartItem cartItem);
}
