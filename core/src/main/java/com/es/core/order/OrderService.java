package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;

import java.util.Optional;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order) throws OutOfStockException;

    Optional<Order> getOrder(Long key);
}
