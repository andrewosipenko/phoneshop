package com.es.core.service.order;

import com.es.core.cart.Cart;
import com.es.core.form.order.OrderForm;
import com.es.core.model.order.Order;
import com.es.core.model.stock.exception.OutOfStockException;

import java.math.BigDecimal;


public interface OrderService {
    Order createOrder(OrderForm orderForm, Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    void updateOrder(Order order, Cart cart);
    BigDecimal getDeliveryPrice();
}
