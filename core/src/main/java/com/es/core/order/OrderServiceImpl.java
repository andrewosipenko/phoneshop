package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.dao.orderDao.OrderDao;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;
import com.es.core.validation.validator.OrderQuantityValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderQuantityValidator orderQuantityValidator;
    @Value("#{new java.math.BigDecimal(${delivery.price})}")
    private BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        setNewOrderItems(order, cart);
        return order;
    }

    @Override
    public synchronized void placeOrder(Order order) throws OutOfStockException {
        if(!orderQuantityValidator.isValidOrderQuantity(order)) {
            throw new OutOfStockException("Some phones are out of the stock\n so they were removed from your cart");
        }
        else{
            order.setStatus(OrderStatus.NEW);
            orderDao.save(order);
        }
    }

    @Override
    public List<OrderItem> getOrderItemList(Cart cart, Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(Map.Entry<Long, Long> entry : cart.getItems().entrySet()){
            Long phoneId = entry.getKey();
            Long quantity = entry.getValue();
            Phone phone = phoneDao.get(phoneId).orElseThrow(NoSuchPhoneException::new);

            orderItems.add(createOrderItem(phone, quantity, order));
        }
        return orderItems;
    }

    @Override
    public void setNewOrderItems(Order order, Cart cart) {
        order.setOrderItems(getOrderItemList(cart, order));
        recalculateOrder(order, cart);
    }

    private void recalculateOrder(Order order, Cart cart){
        order.setSubtotal(cart.getSubtotal());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(order.getSubtotal()));
    }

    private OrderItem createOrderItem(Phone phone, Long quantity, Order order){
        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(phone);
        orderItem.setQuantity(quantity);
        orderItem.setOrder(order);
        return orderItem;
    }
}
