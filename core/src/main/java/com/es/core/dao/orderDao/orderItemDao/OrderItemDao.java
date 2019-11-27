package com.es.core.dao.orderDao.orderItemDao;

import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void saveOrderItems(List<OrderItem> orderItems);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
}
