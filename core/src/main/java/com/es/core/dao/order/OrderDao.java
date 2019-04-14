package com.es.core.dao.order;

import com.es.core.model.order.Order;

import java.util.List;

public interface OrderDao {
    Order findOrderBySecureId(String secureId);

    Order findOrderById(Long id);

    List<Order> findAll();

    void save(Order order);

    Long findOrderIdBySecureId(String secureId);

    void updateOrderStatus(Long id, String status);
}
