package com.es.core.dao;

import com.es.core.model.order.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    private List<Order> orders = new ArrayList<>(); //replace this with db
    @Override
    public Order getOrder(Long id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().get();
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }
}
