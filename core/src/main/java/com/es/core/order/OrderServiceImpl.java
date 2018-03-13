package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.dao.order.OrderDao;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderDao orderDao;

    @Value("#{new java.math.BigDecimal(${deliveryPrice})}")
    BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);

        order.setDeliveryPrice(deliveryPrice);

        BigDecimal subtotal = cart.getCost();
        order.setSubtotal(subtotal);
        order.setTotalPrice(subtotal.add(deliveryPrice));

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity()))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        orderDao.save(order);
    }

    @Override
    public Optional<Order> getOrder(Long key) {
        return orderDao.get(key);
    }
}
