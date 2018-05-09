package com.es.phoneshop.core.order.service;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.order.dao.OrderDao;
import com.es.phoneshop.core.order.model.Order;
import com.es.phoneshop.core.order.model.OrderItem;
import com.es.phoneshop.core.order.model.OrderStatus;
import com.es.phoneshop.core.order.throwable.EmptyCartPlacingOrderException;
import com.es.phoneshop.core.stock.model.Stock;
import com.es.phoneshop.core.stock.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StockService stockService;
    @Resource
    private CartService cartService;
    @Resource
    private OrderDao orderDao;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem item = new OrderItem();
                    item.setQuantity(cartItem.getQuantity());
                    item.setPhone(cartItem.getPhone());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());
        BigDecimal subtotal = cart.getSubtotal();
        BigDecimal deliveryPrice = cart.getDeliveryPrice();
        order.setOrderItems(orderItems);
        order.setSubtotal(subtotal);
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(subtotal.add(deliveryPrice));
        return order;
    }

    @Override
    public Optional<Order> getOrder(String id) {
        return orderDao.get(id);
    }

    @Override
    public void placeOrder(Order order) throws NoStockFoundException, EmptyCartPlacingOrderException {
        if (order.getOrderItems().isEmpty())
            throw new EmptyCartPlacingOrderException();
        for (OrderItem item : order.getOrderItems()) {
            Stock stock = stockService.getStock(item.getPhone()).orElseThrow(NoStockFoundException::new);
            stock.setReserved(stock.getReserved() + item.getQuantity());
            stock.setStock(stock.getStock() - item.getQuantity());
            stockService.update(stock);
        }
        order.setStatus(OrderStatus.NEW);
        String orderId;
        do {
            orderId = generateRandomId();
        } while (!orderDao.isIdUnique(orderId));
        order.setId(orderId);
        orderDao.save(order);
        cartService.clear();
    }

    private String generateRandomId() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        char id[] = new char[10];
        for (int i = 0; i < id.length; i++)
            id[i] = chars.charAt(random.nextInt(chars.length()));
        return new String(id);
    }
}