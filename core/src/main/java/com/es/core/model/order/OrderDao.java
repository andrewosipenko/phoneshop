package com.es.core.model.order;


import com.es.core.exception.OutOfStockException;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(Long key);

    List<Order> findAll(int offset, int limit);

    int orderCount();

    void save(Order order) throws OutOfStockException;
}
