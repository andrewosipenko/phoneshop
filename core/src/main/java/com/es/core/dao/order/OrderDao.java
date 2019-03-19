package com.es.core.dao.order;

import com.es.core.model.order.Order;

import java.util.List;

public interface OrderDao {
    Order findOrder(String secureId);

    List<Order> findAll();

    void save(Order order);

    Long findOrderIdBySecureId(String secureId);
}
