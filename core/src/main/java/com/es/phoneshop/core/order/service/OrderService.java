package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.CartRecord;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.throwable.OutOfStockException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(List<CartRecord> cartRecords, BigDecimal subtotal);
    Optional<Order> getOrder(Long id);
    void placeOrder(Order order) throws OutOfStockException;
}
