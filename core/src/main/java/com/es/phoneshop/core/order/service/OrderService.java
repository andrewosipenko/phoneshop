package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.Cart;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.throwable.OutOfStockException;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
}
