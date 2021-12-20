package com.es.core.model.order;

import com.es.core.exception.OrderNotFindException;
import com.es.core.model.phone.PhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcOrderDaoTest {
    public static final String ACTUAL_SUBTOTAL = "100.0";
    public static final String ACTUAL_DELIVERY_PRICE = "10.0";
    public static final String ACTUAL_TOTAL_PRICE = "110.0";
    public static final String ACTUAL_FIRST_NAME = "Tom";
    public static final String ACTUAL_LAST_NAME = "Jones";
    public static final String ACTUAL_DELIVERY_ADDRESS = "New York";
    public static final String ACTUAL_CONTACT_PHONE_NO = "1234";
    public static final long ACTUAL_ORDER_ID = 4L;
    public static final long EXPECTED_ORDER_ID = 1L;
    public static final String ACTUAL_ADDITIONAL_INFO = "Info 1";

    public static final long ACTUAL_PHONE_ID = 1000L;

    public static final long ACTUAL_ORDER_ITEM_QUANTITY = 2L;
    public static final long ACTUAL_ORDER_ITEM_ID = 1L;
    @Resource
    private OrderDao orderDao;

    @Resource
    private PhoneDao phoneDao;

    private Order actualOrder;

    @Before
    public void init() {
        actualOrder = new Order();
        actualOrder.setId(ACTUAL_ORDER_ID);
        actualOrder.setSubtotal(new BigDecimal(ACTUAL_SUBTOTAL));
        actualOrder.setDeliveryPrice(new BigDecimal(ACTUAL_DELIVERY_PRICE));
        actualOrder.setTotalPrice(new BigDecimal(ACTUAL_TOTAL_PRICE));
        actualOrder.setFirstName(ACTUAL_FIRST_NAME);
        actualOrder.setLastName(ACTUAL_LAST_NAME);
        actualOrder.setDeliveryAddress(ACTUAL_DELIVERY_ADDRESS);
        actualOrder.setContactPhoneNo(ACTUAL_CONTACT_PHONE_NO);
        actualOrder.setStatus(OrderStatus.NEW);
        actualOrder.setAdditionalInfo(ACTUAL_ADDITIONAL_INFO);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(ACTUAL_ORDER_ITEM_ID);
        orderItem.setQuantity(ACTUAL_ORDER_ITEM_QUANTITY);
        orderItem.setPhone(phoneDao.get(ACTUAL_PHONE_ID).get());
        orderItem.setPhoneId(ACTUAL_PHONE_ID);
        List<OrderItem> actualOrderItems = new ArrayList<>();
        actualOrderItems.add(orderItem);
        actualOrder.setOrderItems(actualOrderItems);
    }

    @Test
    public void shouldPhoneDaoExist() {
        assertNotNull(orderDao);
    }

    @Test
    public void shouldGetOrderFromOrderTable(){
        Order expectedOrder = orderDao.getOrder(EXPECTED_ORDER_ID).get();
        expectedOrder.setId(ACTUAL_ORDER_ID);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void shouldGetOrderItemsWhenGetOrder(){
        Order order = orderDao.getOrder(EXPECTED_ORDER_ID).get();
        assertEquals(order.getOrderItems(), actualOrder.getOrderItems());
    }

    @Test
    public void shouldSaveOrder(){
        orderDao.saveOrder(actualOrder);
        Order expectedOrder = orderDao.getOrder(ACTUAL_ORDER_ID).get();
        assertEquals(expectedOrder, actualOrder);
    }

    @Test(expected = OrderNotFindException.class)
    public void shouldDeleteOrder(){
        orderDao.saveOrder(actualOrder);
        orderDao.deleteOrder(ACTUAL_ORDER_ID);
        orderDao.getOrder(ACTUAL_ORDER_ID);
    }
}
