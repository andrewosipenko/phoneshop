package com.es.core.order;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;

public interface OrderService {
    Order createOrder(Cart cart, UserContactInfo userContactInfo);
    void placeOrder(Order order) throws OutOfStockException;
    boolean isValidOrder(Order order);
}
