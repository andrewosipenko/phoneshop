package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    List<OrderItem> getOrderItemList(Cart cart, Order order);
    void setNewOrderItems(Order order, Cart cart);
}
