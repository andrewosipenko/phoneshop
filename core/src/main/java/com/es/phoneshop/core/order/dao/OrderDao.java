package com.es.phoneshop.core.order.dao;

import com.es.phoneshop.core.order.model.Order;

import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(Long id);
    void save(Order order);
}
