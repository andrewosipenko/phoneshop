package com.es.core.service.order;

import com.es.core.exception.OrderNotFoundException;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order) throws OutOfStockException;

    /**
     * @throws IllegalArgumentException if order status already has not NEW, or
     *                                  transferred status is NEW
     */
    void updateOrderStatus(Long key, OrderStatus status) throws OrderNotFoundException;

    Optional<Order> getOrder(Long key);

    List<Order> findAll(int offset, int limit);

    int orderCount();
}
