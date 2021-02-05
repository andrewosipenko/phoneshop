package com.es.core.model.order;

import com.es.core.model.CartModel;
import com.es.core.order.Order;

public interface OrderService {
    Order createOrder(CartModel cart);
    void placeOrder(Order order) throws OutOfStockException;
}
