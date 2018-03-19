package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Person;

public interface OrderService {
    void placeNewOrder(Cart cart, Person person) throws OutOfStockException;
}
