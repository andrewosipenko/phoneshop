package com.es.core.dao.orderDao;


import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;


public interface OrderDao {

    Optional<Order> get(Long key);

    Long save(Order order);

    List<Order> findAll();

    void updateOrderStatus(Long id, OrderStatus orderStatus);

}
