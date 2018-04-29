package com.es.core.dao.orderDao;

import com.es.core.model.order.Order;

public interface OrderDao {
    void save(Order order);
    Order get(Long orderId);
}
