package com.es.core.order;

import com.es.core.model.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {

    void placeOrder(Order order);

    void deliverOrder(Order order);

    void rejectOrder(Order order);

    Optional<Order> getOrderById(Long id);

    Optional<Order> getOrderByUUID(UUID uuid);

    List<Order> getOrders();
}
