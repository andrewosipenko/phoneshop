package com.es.core.services.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exceptions.OutOfStockException;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    Order getOrder(Long orderId);
}