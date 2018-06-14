package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Cart cart);

    void placeOrder(Order order) throws OutOfStockException;

    Order getOrderById(Long id);

    Order getOrderByUUID(UUID uuid);

    void removeItems(Order order, List<OrderItem> items);

    List<Order> getOrders();
}
