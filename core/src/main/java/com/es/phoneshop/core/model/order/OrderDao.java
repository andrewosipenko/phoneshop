package com.es.phoneshop.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void insertOrder(final Order order);
    List<Order> findAll();
    Optional<Order> findById(Long orderId);
    void changeStatus(Long orderId, Integer status);
}
