package com.es.core.order;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@PropertySource("classpath:conf/application.properties")
public class DefaultOrderService implements OrderService {
    @Value("#{new java.math.BigDecimal('${delivery.price}')}")
    private BigDecimal deliveryPrice;

    @Resource
    private StockService stockService;

    @Resource
    private OrderDao orderDao;


    @Override
    public Order createOrder(Cart cart, UserContactInfo userContactInfo) {
        Order order = new Order();
        if(userContactInfo == null){
            order.setFirstName("");
            order.setLastName("");
            order.setDeliveryAddress("");
            order.setContactPhoneNo("");
        } else {
            order.setFirstName(userContactInfo.getFirstName());
            order.setLastName(userContactInfo.getLastName());
            order.setDeliveryAddress(userContactInfo.getDeliveryAddress());
            order.setContactPhoneNo(userContactInfo.getContactPhoneNo());
            order.setAdditionalInfo(userContactInfo.getAdditionalInfo());
        }
        order.setId(orderDao.getLastOrderId() + 1);
        order.setStatus(OrderStatus.NEW);
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(cart.getTotalCost()));
        List<OrderItem> orderItemList = new ArrayList<>();
        Long lastOrderItemId = orderDao.getLastOrderItemId();
        AtomicInteger iter = new AtomicInteger();
        cart.getCartItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(lastOrderItemId + iter.incrementAndGet());
            orderItem.setQuantity(Long.valueOf(cartItem.getQuantity()));
            orderItem.setPrice(cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setPhoneId(cartItem.getPhone().getId());
            orderItem.setPhone(cartItem.getPhone());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        });
        order.setOrderItems(orderItemList);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        order.getOrderItems().forEach(orderItem -> {
            if (orderItem.getQuantity() > stockService.getAvailablePhoneStock(orderItem.getPhoneId())) {
                throw new OutOfStockException(orderItem.getPhoneId().toString());
            }
        });
        orderDao.saveOrder(order);
    }

}
