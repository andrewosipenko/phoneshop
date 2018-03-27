package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(long id);
    void save(Order order);
    void decreaseProductStock(Order order);
    List<Order> getOrders();
    void changeOrderStatus(long id, OrderStatus orderStatus);
}
