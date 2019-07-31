package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void insert(Order order);

    Order loadOrderById(long orderId);

    Optional<Long> getLastInsertedId();

    List<Order> loadAllOrders();

    void updateOrderStatus(long orderId, OrderStatus orderStatus);
}
