package com.es.core.service.cart;

import com.es.core.model.cart.Cart;

public interface PriceService {
    void recalculateCart(Cart cart);
}
