package com.es.phoneshop.core.order;

import com.es.phoneshop.core.model.order.Order;

public interface OrderService {
    Order createOrder();
    void placeOrder(Order order);
    Long getDeliveryPrice();
    boolean checkOrderItemsStock();
}
