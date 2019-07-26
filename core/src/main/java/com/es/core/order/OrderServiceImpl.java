package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.ProductDao;
import com.es.core.model.order.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@PropertySource("/WEB-INF/conf/application.properties")
public class OrderServiceImpl implements OrderService {

    @Resource
    private ProductDao productDao;

    @Resource
    private CartService cartService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Value("${delivery.price}")
    private double deliveryPrice;

    @Override
    public Order createOrder(Order order, Cart cart) {
        order.setDeliveryPrice(new BigDecimal(deliveryPrice));
        order.setSubtotal(new BigDecimal(cartService.getCart().getTotalPrice()));
        order.setTotalPrice(new BigDecimal(deliveryPrice + cartService.getCart().getTotalPrice()));
        order.setStatus(OrderStatus.NEW);
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : cartService.getCart().getProducts().entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPhone(productDao.loadPhoneById(entry.getKey()));
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        order.getOrderItems().forEach((orderItem) -> {
            orderItemDao.insert(orderItem);
        });
        orderDao.insert(order);
    }
}
