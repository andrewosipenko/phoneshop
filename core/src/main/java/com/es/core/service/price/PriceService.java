package com.es.core.service.price;

import com.es.core.model.cart.Cart;

public interface PriceService {
    void recalculatePrice(Cart cart);
}
