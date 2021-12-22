package com.es.core.order;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import javax.servlet.http.HttpSession;

public interface OrderService {
    Order createOrder(Cart cart, UserContactInfo userContactInfo);
    void placeOrder(Order order, HttpSession session) throws OutOfStockException;
    void changeStatus(Long id, OrderStatus orderStatus);
}
