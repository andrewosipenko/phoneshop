package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.dao.orderItem.OrderItemDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderOwnerInfo;
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
    private OrderItemDao orderItemDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order createOrder(Cart cart, OrderOwnerInfo orderOwnerInfo, BigDecimal deliveryPrice) {
        Order order = new Order();
        order.setSubtotal(cart.getTotalPrice());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(order.getSubtotal().add(deliveryPrice));
        List<OrderItem> orderItems = cart.getCartItems()
                .stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), cartItem.getQuantity()))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        order.setFirstName(orderOwnerInfo.getName());
        order.setLastName(orderOwnerInfo.getLastName());
        order.setDeliveryAddress(orderOwnerInfo.getAddress());
        order.setAdditionalInfo(orderOwnerInfo.getAdditionalInfo());
        order.setContactPhoneNo(orderOwnerInfo.getPhone());
        order.setSecureId(UUID.randomUUID().toString());

        orderDao.save(order);
        order.setId(orderDao.findIdToOrder(order.getSecureId()));
        orderItemDao.save(order.getOrderItems(), order.getId());

        return order;
    }

    @Override
    public Order findOrderBuSecureId(String secureId) {
        return orderDao.findOrder(secureId);
    }

    @Override
    public void placeOrder(Order order) {
        throw new UnsupportedOperationException("TODO");
    }
}
