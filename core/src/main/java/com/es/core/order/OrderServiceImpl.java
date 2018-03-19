package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.cost.CostService;
import com.es.core.model.order.*;
import com.es.core.model.phone.Phone;
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
    private CostService costService;

    @Resource
    private OrderDao orderDao;

    @Value("${delivery.price}")
    private BigDecimal delivery;

    @Override
    public void placeNewOrder(Cart cart, Person person) throws OutOfStockException {
        Order order = createOrder(cart, person);
        orderDao.save(order);
    }

    private Order createOrder(Cart cart, Person person) {
        Order order = new Order();
        BigDecimal subtotal = costService.getCost();
        order.setOrderItems(createOrderItems(order, cart));
        order.setFirstName(person.getFirstName());
        order.setLastName(person.getLastName());
        order.setDeliveryAddress(person.getAddress());
        order.setContactPhoneNo(person.getPhone());
        order.setAdditionInfo(person.getAdditionalInfo());
        order.setSubtotal(subtotal);
        order.setDeliveryPrice(delivery);
        order.setTotalPrice(subtotal.add(delivery));
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        List<OrderItem> orderItems = new ArrayList<>();
        Map<Phone, Long> items = cart.getItems();
        items.keySet().forEach(phone -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPhone(phone);
            orderItem.setQuantity(items.get(phone));
            orderItems.add(orderItem);
        });
        return orderItems;
    }
}
