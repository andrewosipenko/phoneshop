package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.exception.PhoneInCartNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.phone.Stock;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart) throws OutOfStockException, PhoneInCartNotFoundException;
    void placeOrder(Order order) throws OutOfStockException, EmptyOrderListException, PhoneInCartNotFoundException;
    List<Stock> validateCart(Cart cart) throws PhoneInCartNotFoundException, OutOfStockException;
}
