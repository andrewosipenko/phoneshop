package com.es.core.dao.orderItem;

import com.es.core.model.order.OrderItem;

public interface OrderItemDao {
    void save(OrderItem orderItem, Long orderId);
}
