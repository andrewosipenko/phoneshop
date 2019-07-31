package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;


public interface OrderService {
    Order createOrder(Order order, Cart cart);

    void placeOrder(Order order, Cart cart) throws OutOfStockException;

    void updateStatus(long orderId, String status);
}
