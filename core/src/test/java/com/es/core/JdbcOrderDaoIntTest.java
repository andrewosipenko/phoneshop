package com.es.core;

import com.es.core.cart.Cart;
import com.es.core.dao.orderDao.JdbcOrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.order.exception.NoSuchOrderException;
import com.es.core.service.order.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcOrderDaoIntTest {
    @Resource
    private OrderService orderService;
    @Resource
    private JdbcOrderDao orderDao;

    private final Long EXISTING_PHONE_ID_TO_SAVE_1 = 1002L;
    private final Long EXISTING_PHONE_ID_TO_SAVE_2 = 1003L;
    private final Long EXISTING_PHONE_ID_TO_GET = 1003L;
    private final Long EXISTING_ORDER_ID = 1000L;
    private final Long NOT_EXISTING_ORDER_ID = 2000L;
    private final Long ORDER_ITEM_ID_BELONGS_TO_ORDER = 500L;
    private final Long PHONE_QUANTITY_1 = 2L;
    private final Long PHONE_QUANTITY_2 = 4L;
    private final String FIRST_NAME = "testFName";
    private final String LAST_NAME = "testLName";
    private final String DELIVERY_ADDRESS = "testAddress";
    private final String PHONE_NUMBER = "testPhone";
    private final String EXTRA_INFO = "testExtraInfo";
    private final OrderStatus ORDER_STATUS = OrderStatus.NEW;
    private Cart cart;
    private Order order;

    @Test
    public void save(){
        initOrder();
        orderDao.save(order);

        Assert.assertTrue(order.getId() != null);
        order = orderDao.get(order.getId()).orElseThrow(NoSuchOrderException::new);

        List<OrderItem> orderItems = order.getOrderItems();
        Assert.assertTrue(orderItems.size() == 2);
        Assert.assertTrue(orderItems.get(0).getOrder().getId().equals(order.getId()));
        Assert.assertTrue(orderItems.get(1).getOrder().getId().equals(order.getId()));

        Set<Long> phoneIds = getPhoneIds(orderItems);
        Assert.assertTrue(phoneIds.contains(EXISTING_PHONE_ID_TO_SAVE_1));
        Assert.assertTrue(phoneIds.contains(EXISTING_PHONE_ID_TO_SAVE_2));

        assertOrderEquals(FIRST_NAME, LAST_NAME, DELIVERY_ADDRESS, PHONE_NUMBER, EXTRA_INFO, ORDER_STATUS);
    }

    @Test
    public void getExistingOrder(){
        Order order = orderDao.get(EXISTING_ORDER_ID).orElseThrow(NoSuchOrderException::new);
        Assert.assertTrue(order.getId().equals(EXISTING_ORDER_ID));
        List<OrderItem> orderItems = order.getOrderItems();
        Assert.assertTrue(orderItems.size() == 1);
        OrderItem orderItem = orderItems.get(0);
        Assert.assertTrue(orderItem.getId().equals(ORDER_ITEM_ID_BELONGS_TO_ORDER));
        Assert.assertTrue(orderItem.getPhone().getId().equals(EXISTING_PHONE_ID_TO_GET));
    }

    @Test(expected = NoSuchOrderException.class)
    public void getNotExistingOrder(){
       Order order =  orderDao.get(NOT_EXISTING_ORDER_ID).orElseThrow(NoSuchOrderException::new);
    }

    private void initOrder(){
        cart = new Cart();
        cart.addPhone(EXISTING_PHONE_ID_TO_SAVE_1, PHONE_QUANTITY_1);
        cart.addPhone(EXISTING_PHONE_ID_TO_SAVE_2, PHONE_QUANTITY_2);
        order = orderService.createOrder(cart);
        order.setStatus(ORDER_STATUS);
        order.setContactPhoneNo(PHONE_NUMBER);
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setExtraInfo(EXTRA_INFO);
    }

    private Set<Long> getPhoneIds(List<OrderItem> orderItems){
        return orderItems.stream().collect(Collectors.toMap(
                OrderItem -> OrderItem.getPhone().getId(),
                OrderItem -> OrderItem)).
                keySet();
    }

    private void assertOrderEquals(String firstName, String lastName, String deliveryAddress,
                                      String phone, String extraInfo, OrderStatus orderStatus){
        Assert.assertTrue(order.getContactPhoneNo().equals(phone));
        Assert.assertTrue(order.getDeliveryAddress().equals(deliveryAddress));
        Assert.assertTrue(order.getFirstName().equals(firstName));
        Assert.assertTrue(order.getLastName().equals(lastName));
        Assert.assertTrue(order.getStatus().equals(orderStatus));
        Assert.assertTrue(order.getExtraInfo().equals(extraInfo));
    }
}