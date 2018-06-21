package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.PriceService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.StockDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StockDao stockDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private PriceService priceService;


    private OrderItem createOrderItem(Order order, Phone phone, Long quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setPhone(phone);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    private List<OrderItem> generateOrderItemsList(Cart cart, Order order){
        return cart.getProducts().values()
                .stream()
                .map((ce)-> createOrderItem(order, ce.getPhone(), ce.getQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeItems(Order order, List<OrderItem> itemsToDelete){
        List<OrderItem> orderItems = order.getOrderItems();
        itemsToDelete.forEach(orderItems::remove);
        priceService.updateOrderPrice(order);
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setOrderItems(generateOrderItemsList(cart, order));
        order.setSubtotal(priceService.obtainCartSubtotal(cart));
        order.setTotalPrice(priceService.obtainCartTotal(cart));
        order.setDeliveryPrice(priceService.getDeliveryPrice());

        return order;
    }

    @Override
    @Transactional
    public void placeOrder(Order order){
        order.setOrderUUID(UUID.randomUUID());
        order.setStatus(OrderStatus.NEW);
        order.setPlacementDate(Date.from(Instant.now()));
        orderDao.placeOrder(order);
        stockDao.reserveStocks(order);
    }

    @Override
    @Transactional
    public void rejectOrder(Order order) {
        stockDao.rejectReserved(order);
        orderDao.rejectOrder(order);
    }

    @Override
    @Transactional
    public void deliverOrder(Order order) {
        stockDao.applyReserved(order);
        orderDao.deliverOrder(order);
    }

    @Override
    public Order getOrderByUUID(UUID uuid){
        return orderDao.getOrderByUUID(uuid).orElseThrow(()->new IllegalArgumentException("Order not found"));
    }

    @Override
    public Order getOrderById(Long id){
        return orderDao.getOrderById(id).orElseThrow(()->new IllegalArgumentException("Order not found"));
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }
}
