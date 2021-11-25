package com.es.core.service.order;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);

    Long placeOrder(Order order) throws OutOfStockException;

    Order getOrder(Long orderId);

    List<Order> getOrders();

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
