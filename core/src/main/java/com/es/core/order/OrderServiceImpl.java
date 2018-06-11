package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.PriceService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StockDao stockDao;

    @Resource
    private PriceService priceService;

    private List<Order> orders;

    private Long lastId;


    @PostConstruct
    public void init(){
        orders = new LinkedList<>();
        lastId = (long) orders.size();
    }

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

    private boolean isOutOfStock(OrderItem item, Stock stock){
         return stock.getStock() - stock.getReserved() - item.getQuantity() <= 0;
    }

    private List<Phone> obtainPhones(Order order){
        return order.getOrderItems().stream().map(OrderItem::getPhone).collect(Collectors.toList());
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setId(lastId++);
        order.setOrderItems(generateOrderItemsList(cart, order));
        order.setSubtotal(priceService.obtainCartSubtotal(cart));
        order.setTotalPrice(priceService.obtainCartTotal(cart));
        order.setDeliveryPrice(priceService.getDeliveryPrice());

        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        order.setUUID(UUID.randomUUID());
        orders.add(order);
    }

    @Override
    public Order getOrderByUUID(UUID uuid){
        return orders.stream()
                .filter((o)-> o.getUUID().equals(uuid))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Order doesn't exist"));
    }

    @Override
    public Order getOrderById(Long id){
        return orders.stream()
                .filter((o)-> o.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Order doesn't exist"));
    }

}
