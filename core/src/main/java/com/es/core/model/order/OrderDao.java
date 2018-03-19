package com.es.core.model.order;

import com.es.core.order.OutOfStockException;

public interface OrderDao {
    void save(Order order) throws OutOfStockException;
}
