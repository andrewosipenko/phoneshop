package com.es.phoneshop.core.order;

import com.es.phoneshop.core.cart.CartService;
import com.es.phoneshop.core.model.order.Order;
import com.es.phoneshop.core.model.order.OrderDao;
import com.es.phoneshop.core.model.order.OrderItem;
import com.es.phoneshop.core.model.order.OrderStatus;
import com.es.phoneshop.core.model.phone.Phone;
import com.es.phoneshop.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service
@PropertySource("classpath:application.properties")
public class OrderServiceImpl implements OrderService {
    private static Long orderId = 0L;

    @Value("${delivery.price}")
    private Long deliveryPrice;

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private OrderDao orderDao;

    @Override
    public Order createOrder() {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for(Map.Entry<Phone, Long> entry: cartService.getPhoneMap().entrySet()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPhone(entry.getKey());
            item.setQuantity(entry.getValue());
            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        setPriceInfo(order);
        order.setId(++orderId);
        order.setStatus(OrderStatus.NEW);
        order.setDate(new Date());

        return order;
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.insertOrder(order);
        cartService.getCart().getCartItems().clear();

        for(OrderItem item: order.getOrderItems()) {
            phoneDao.decreaseStock(item.getPhone().getId(), item.getQuantity());
        }
    }

    @Override
    public Long getDeliveryPrice() {
        return deliveryPrice;
    }

    public boolean checkOrderItemsStock() {
        boolean allStocksEnough = true;

        for(Map.Entry<Long, Long> cartEntry: new HashMap<>( cartService.getCart().getCartItems()).entrySet()) {
            if(cartEntry.getValue() > phoneDao.getStock(cartEntry.getKey()).get().getStock()) {
                allStocksEnough = false;
                cartService.remove(cartEntry.getKey());
            }
        }

        return allStocksEnough;
    }

    private void setPriceInfo(Order order) {
        BigDecimal subtotal = new BigDecimal(cartService.getOverallPrice());
        BigDecimal deliveryPrice = new BigDecimal(getDeliveryPrice());
        BigDecimal totalPrice = subtotal.add(deliveryPrice);

        order.setSubtotal(subtotal);
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(totalPrice);
    }
}
