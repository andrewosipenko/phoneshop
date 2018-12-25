package com.es.core.services.order;

import com.es.core.dao.OrderDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.order.OrderItem;
import com.es.core.services.phone.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final PhoneService phoneService;
    private final OrderPriceService orderPriceService;
    private final OrderDao orderDao;
    private long ordersCount = 0;

    @Autowired
    public OrderServiceImpl(PhoneService phoneService, OrderPriceService orderPriceService, OrderDao orderDao) {
        this.phoneService = phoneService;
        this.orderPriceService = orderPriceService;
        this.orderDao = orderDao;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setOrderItems(convertCartItemsToOrderItems(cart.getCartItems(), order));
        order.setSubtotal(orderPriceService.getSubtotalOf(order));
        order.setDeliveryPrice(orderPriceService.getDeliveryPrice());
        order.setTotalPrice(orderPriceService.getTotalPriceOf(order));
        return order;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderDao.getOrder(orderId);
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        synchronized (this) {
            order.setId(ordersCount++);
        }
        orderDao.addOrder(order);
    }

    List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPhone(phoneService.get(cartItem.getPhoneId()).get());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        });
        return orderItems;
    }
}
