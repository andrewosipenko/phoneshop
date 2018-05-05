package com.es.core.dao.orderDao;

import com.es.core.model.order.Order;

import java.util.Optional;

public interface OrderDao {
    void save(Order order);
    Optional<Order> get(Long orderId);
    Optional<Order> get(String orderKey);
    Optional<String> getOrderKey(Long orderId);
}
