package com.es.core.service.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.customer.CustomerInfo;

import java.math.BigDecimal;

public interface OrderService {
    Order createOrder(Cart cart, CustomerInfo customerInfo, BigDecimal deliveryPrice);

    Order findOrderBuSecureId(String secureId);

    void placeOrder(Order order);
}
