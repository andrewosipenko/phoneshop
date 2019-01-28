package com.es.core.service.order;

import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    List<String> getOrderStatusNamesExcept(OrderStatus orderStatus);
}
