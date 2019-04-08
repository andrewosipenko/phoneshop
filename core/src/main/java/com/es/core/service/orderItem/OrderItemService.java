package com.es.core.service.orderItem;

import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemService {
    void save(List<OrderItem> orderItems, Long orderId);
}
