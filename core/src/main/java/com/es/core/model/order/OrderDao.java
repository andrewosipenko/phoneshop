package com.es.core.model.order;

import com.es.core.exception.OrderNotFindException;

import java.util.Optional;

public interface OrderDao {
    void deleteOrder(Long id) throws OrderNotFindException;

    void saveOrder(Order order) throws OrderNotFindException;

    Optional<Order> getOrder(Long id) throws OrderNotFindException;

    Optional<Order> getOrderBySecureId(String secureId) throws OrderNotFindException;

    Long getLastOrderItemId();

    Long getLastOrderId();
}
