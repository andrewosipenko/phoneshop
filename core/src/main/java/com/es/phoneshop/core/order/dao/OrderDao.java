package com.es.phoneshop.core.order.dao;

import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(String id);

    void save(Order order);

    boolean isIdUnique(String id);

    List<Order> getAll();

    void updateStatus(String id, OrderStatus status);
}
