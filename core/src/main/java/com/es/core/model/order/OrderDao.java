package com.es.core.model.order;


import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> getOrderById(Long id);

    List<Order> findAll(int offset, int limit);

    int orderCount();

    void save(Order order);
}
