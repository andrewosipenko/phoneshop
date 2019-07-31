package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.ProductDao;
import com.es.core.model.StockDao;
import com.es.core.model.order.*;
import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private ProductDao productDao;

    @Resource
    private CartService cartService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private Environment environment;

    @Resource
    private StockDao stockDao;

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
    public void placeOrder(Order order, Cart cart) throws OutOfStockException {
        long countOfAvailablePhonesInCart = order.getOrderItems().stream()
                .filter(orderItem -> {
                    Stock stock = stockDao.loadStockOfPhoneByPhoneId(orderItem.getPhone().getId());
                    return orderItem.getQuantity() <= stock.getStock() - stock.getReserved();
                }).count();

        if (countOfAvailablePhonesInCart != order.getOrderItems().size()) {
            order.getOrderItems().forEach((orderItem -> {
                Stock stock = stockDao.loadStockOfPhoneByPhoneId(orderItem.getPhone().getId());
                if (orderItem.getQuantity() > stock.getStock() - stock.getReserved()) {
                    cart.getProducts().remove(orderItem.getPhone().getId());
                }
            }));
            throw new OutOfStockException();
        }
        cart.getProducts().clear();
        order.setId(orderDao.getLastInsertedId().orElse(0L) + 1);
        order.setLocalDateTime(LocalDateTime.now().withNano(0));
        orderDao.insert(order);
        order.getOrderItems().forEach(orderItem -> {
            orderItemDao.insert(orderItem);
            stockDao.reservePhonesByPhoneId(orderItem.getPhone().getId(), orderItem.getQuantity());
        });
    }

    @Override
    public void updateStatus(long orderId, String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        orderDao.updateOrderStatus(orderId, orderStatus);
    }
}
