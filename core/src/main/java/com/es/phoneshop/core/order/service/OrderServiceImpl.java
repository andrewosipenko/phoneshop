package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.throwable.OutOfStockException;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(Cart cart) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        throw new UnsupportedOperationException("TODO");
    }
}
