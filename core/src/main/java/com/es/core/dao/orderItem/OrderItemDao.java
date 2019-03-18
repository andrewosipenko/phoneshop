package com.es.core.dao.orderItem;

import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void save(List<OrderItem> orderItems, Long orderId);
}
