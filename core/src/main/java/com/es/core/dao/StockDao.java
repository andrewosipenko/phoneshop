package com.es.core.dao;

import com.es.core.model.order.OrderItem;

import java.util.List;

public interface StockDao {
    Integer getStockFor(Long key);

    void increaseReservationForOrderItems(List<OrderItem> orderItems);

    void decreaseStockForOrderItems(List<OrderItem> orderItems);
}
