package com.es.core.service.order;

import com.es.core.cart.Cart;
import com.es.core.dao.orderDao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.exception.OutOfStockException;
import com.es.core.service.order.orderItem.OrderItemService;
import com.es.core.service.stock.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StockService stockService;
    @Resource
    private OrderItemService orderItemService;

    @Value("#{new java.math.BigDecimal(${delivery.price})}")
    private BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        orderItemService.setNewOrderItems(order, cart);
        recalculateOrder(order, cart);
        return order;
    }

    @Override
    public synchronized void placeOrder(Order order) throws OutOfStockException {
        stockService.updateStocks(order);
        order.setStatus(OrderStatus.NEW);
        orderDao.save(order);
    }

    private void recalculateOrder(Order order, Cart cart){
        order.setSubtotal(cart.getSubtotal());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(order.getSubtotal()));
    }
}
