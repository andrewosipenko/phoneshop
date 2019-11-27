package com.es.core.service.order.orderItem;

import com.es.core.cart.Cart;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getOrderItemList(Cart cart);
}
