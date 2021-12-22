package com.es.core.order;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    @Resource
    private CartService cartService;

    @Override
    public Order createOrder(Cart cart, UserContactInfo userContactInfo) {
        Order order = new Order();
        if (userContactInfo == null) {
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
        order.setSecureId(UUID.randomUUID());
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

    @Transactional
    @Override
    public void placeOrder(Order order, HttpSession session) throws OutOfStockException {
        Cart cart = cartService.getCart(session);
        List<Long> errorsPhoneIds = stockService.updateStocks(order);
        if (!errorsPhoneIds.isEmpty()) {
            errorsPhoneIds.forEach(phoneId -> cartService.remove(phoneId, cart));
            throw new OutOfStockException(order.getId().toString());
        } else {
            orderDao.saveOrder(order);
        }
    }

    @Override
    public void changeStatus(Long id, OrderStatus orderStatus) {
        orderDao.changeStatus(id, orderStatus);
    }
}
