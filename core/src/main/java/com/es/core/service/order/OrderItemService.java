package com.es.core.service.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getOrderItemList(Cart cart);
}
