package com.es.core.service.cart;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;

public interface PriceService {
    void recalculateCart(Cart cart);
    void recalculateOrder(Order order);
}
