package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.order.Person;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();
    Optional<Order> getOrder(long id);
    long placeNewOrderAndReturnId(Cart cart, Person person) throws OutOfStockException;
    void changeOrderStatus(long id, OrderStatus orderStatus);
}
