package com.es.core.model.order;

import java.util.List;

public interface OrderItemDao {
    void insert(OrderItem orderItem);

    OrderItem loadOrderItemById(long orderItemId);

    List<OrderItem> loadOrderItemsOfOrderByOrderId(long orderId);
}
