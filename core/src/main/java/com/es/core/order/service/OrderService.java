package com.es.core.order.service;

import com.es.core.cart.entity.Cart;
import com.es.core.order.entity.Order;
import com.es.core.order.service.exception.OutOfStockException;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(Cart cart);
    void completeOrder(Order orderWithCustomerData, Cart cart);
    Map<Long,Long> getAvailableStocksForOutOfStockPhones(Order order);
    void placeOrder(Order order) throws OutOfStockException;
}
