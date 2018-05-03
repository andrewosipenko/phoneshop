package com.es.core.service.order;

import com.es.core.cart.Cart;
import com.es.core.dao.orderDao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
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
        updateOrder(order, cart);
        return order;
    }

    @Override
    public synchronized void placeOrder(Order order) throws OutOfStockException {
        stockService.updateStocks(order);
        order.setStatus(OrderStatus.NEW);
        orderDao.save(order);
    }

    @Override
    public void updateOrder(Order order, Cart cart){
        order.setOrderItems(orderItemService.getOrderItemList(cart));
        recalculateOrder(order);
    }

    private void recalculateOrder(Order order){
        BigDecimal subtotal = BigDecimal.ZERO;
        for(OrderItem orderItem : order.getOrderItems()){
            BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());
            BigDecimal phonePrice = orderItem.getPhone().getPrice();
            subtotal = subtotal.add(phonePrice.multiply(quantity));
        }

        order.setSubtotal(subtotal);
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(subtotal));
    }
}
