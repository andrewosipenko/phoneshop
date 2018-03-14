package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.exception.OrderNotFoundException;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
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
                .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity(),
                        getTotalCost(cartItem.getPhone(), cartItem.getQuantity())))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        return order;
    }

    private BigDecimal getTotalCost(Phone phone, Long quantity) {
        if (phone.getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return phone.getPrice().multiply(new BigDecimal(quantity));
    }

    private void setTotalCost(Order order) {
        order.getOrderItems()
                .forEach(orderItem -> orderItem.setTotal(getTotalCost(orderItem.getPhone(), orderItem.getQuantity())));
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        orderDao.save(order);
    }

    @Override
    public void updateOrderStatus(Long key, OrderStatus status) throws OrderNotFoundException {
        Order order = getOrder(key).orElseThrow(OrderNotFoundException::new);

        if (status == OrderStatus.NEW || order.getStatus() != OrderStatus.NEW) {
            throw new IllegalArgumentException();
        }

        order.setStatus(status);

        try {
            orderDao.save(order);
        } catch (OutOfStockException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Order> getOrder(Long key) {
        Optional<Order> orderOptional = orderDao.get(key);
        if (orderOptional.isPresent()) {
            setTotalCost(orderOptional.get());
        }
        return orderOptional;
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        List<Order> orderList = orderDao.findAll(offset, limit);
        orderList.forEach(order -> setTotalCost(order));
        return orderList;
    }

    @Override
    public int orderCount() {
        return orderDao.orderCount();
    }
}
