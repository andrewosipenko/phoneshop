package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.cost.CostService;
import com.es.core.model.order.*;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private CostService costService;

    @Resource
    private PhoneService phoneService;

    @Resource
    private OrderDao orderDao;

    @Value("${delivery.price}")
    private BigDecimal delivery;

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getOrders();
    }

    @Override
    public Optional<Order> getOrder(long id) {
        return orderDao.get(id);
    }

    @Override
    public long placeNewOrderAndReturnId(Cart cart, Person person) throws OutOfStockException {
        Order order = createOrder(cart, person);
        checkItemsInOrder(order);
        orderDao.save(order);
        orderDao.decreaseProductStock(order);
        return order.getId();
    }

    @Override
    public void changeOrderStatus(long id, OrderStatus orderStatus) {
        orderDao.changeOrderStatus(id, orderStatus);
    }

    private Order createOrder(Cart cart, Person person) {
        Order order = new Order();
        BigDecimal subtotal = costService.getCost(cart);
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
            orderItem.setPhoneId(phone.getId());
            orderItem.setQuantity(items.get(phone));
            orderItems.add(orderItem);
        });
        return orderItems;
    }

    private void checkItemsInOrder(Order order) throws OutOfStockException {
        Map<Long, Long> items = order.getOrderItems().stream()
                .collect(toMap(OrderItem::getPhoneId, OrderItem::getQuantity));
        for (long phoneId : items.keySet()) {
            long stockCount = phoneService.countProductInStock(phoneId);
            if (stockCount < items.get(phoneId)) {
                throw new OutOfStockException();
            }
        }
    }
}
