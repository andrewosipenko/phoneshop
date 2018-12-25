package com.es.core.dao;

import com.es.core.model.order.Order;

public interface OrderDao {
    Order getOrder(Long id);
    void addOrder(Order order);
}
