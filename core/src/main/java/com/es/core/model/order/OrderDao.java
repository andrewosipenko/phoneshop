package com.es.core.model.order;

public interface OrderDao {
    void insert(Order order);

    Order loadOrderById(long orderId);
}
