package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.throwable.OutOfStockException;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Cart cart);

    Optional<Order> getOrder(String id);

    void placeOrder(Order order) throws OutOfStockException;

    void setOrderStatus(String orderId, OrderStatus status);

    List<Order> getAllOrders();
}
