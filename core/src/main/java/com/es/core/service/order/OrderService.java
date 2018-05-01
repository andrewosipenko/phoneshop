package com.es.core.service.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.stock.exception.OutOfStockException;


public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
}
