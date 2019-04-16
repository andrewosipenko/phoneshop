package com.es.core.service.order;

import com.es.core.form.order.OrderForm;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderForm orderForm, Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    OrderForm createOrderForm(Cart cart);
    Order getOrder(Long key);
    Order getOrder(String key);
    String getOrderKey(Long orderId);
    void updateOrderForm(OrderForm orderForm, Cart cart);
    BigDecimal getDeliveryPrice();
    List<Order> findAll();
    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
