package com.es.core.service;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order);
}
