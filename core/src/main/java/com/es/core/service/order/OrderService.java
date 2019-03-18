package com.es.core.service.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderOwnerInfo;

import java.math.BigDecimal;

public interface OrderService {
    Order createOrder(Cart cart, OrderOwnerInfo orderOwnerInfo, BigDecimal deliveryPrice);

    Order findOrderBuSecureId(String secureId);

    void placeOrder(Order order);
}
