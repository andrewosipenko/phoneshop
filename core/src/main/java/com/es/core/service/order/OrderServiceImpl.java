package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.customer.CustomerInfo;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.orderItem.OrderItemService;
import com.es.core.service.stock.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemService orderItemService;

    @Resource
    private StockService stockService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order createOrder(Cart cart, CustomerInfo customerInfo, BigDecimal deliveryPrice) {
        Order order = new Order();
        order.setSubtotal(cart.getTotalPrice());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(order.getSubtotal().add(deliveryPrice));
        List<OrderItem> orderItems = cart.getCartItems()
                .stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), cartItem.getQuantity()))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        order.setFirstName(customerInfo.getName());
        order.setLastName(customerInfo.getLastName());
        order.setDeliveryAddress(customerInfo.getAddress());
        order.setAdditionalInfo(customerInfo.getAdditionalInfo());
        order.setContactPhoneNo(customerInfo.getPhone());
        order.setSecureId(UUID.randomUUID().toString());

        orderDao.save(order);
        order.setId(orderDao.findOrderIdBySecureId(order.getSecureId()));
        orderItemService.save(order.getOrderItems(), order.getId());

        return order;
    }

    @Override
    public Order findOrderBuSecureId(String secureId) {
        return orderDao.findOrderBySecureId(secureId);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public void placeOrder(Order order, String status) {
        if (status == null) {
            throw new IllegalArgumentException();
        }
        order.setStatus(OrderStatus.valueOf(status));
        orderDao.updateOrderStatus(order.getId(), status);
        if (status.equals(OrderStatus.DELIVERED.toString())) {
            order.getOrderItems()
                    .forEach(orderItem -> stockService.deleteReserved(orderItem.getPhone().getId(), orderItem.getQuantity()));
        } else {
            order.getOrderItems()
                    .forEach(orderItem -> stockService.replaceReservedToStock(orderItem.getPhone().getId(), orderItem.getQuantity()));
        }
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }
}
