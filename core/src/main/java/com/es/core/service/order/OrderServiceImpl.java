package com.es.core.service.order;

import com.es.core.dao.orderDao.OrderDao;
import com.es.core.dao.stockDao.StockDao;
import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.cart.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StockDao stockDao;

    @Resource
    private CartService cartService;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setOrderCreationDate(new Date(new java.util.Date().getTime()));
        order.setDeliveryPrice(cart.getDeliveryPrice());
        order.setSubtotal(cart.getSubtotalPrice());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderItems(cart.getCartItems()
                .stream()
                .map(cartItem ->
                        new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity()))
                .collect(Collectors.toList()));
        return order;
    }

    @Override
    public Long placeOrder(Order order) throws OutOfStockException {
        Long orderId = orderDao.save(order);
        cartService.clearCart();
        order.getOrderItems()
                .forEach(orderItem ->
                        stockDao.reducePhoneStock(orderItem.getPhone().getId(), orderItem.getQuantity()));
        return orderId;

    }

    @Override
    public Order getOrder(Long orderId) {
        return orderDao.get(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.findAll();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        orderDao.updateOrderStatus(orderId, orderStatus);
    }
}
