package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcOrderDaoImplTest {
    private static final String SECURE_ID = "qwerty";
    private static final String FIRST_NAME = "Vlad";
    private static final long ID = 1L;
    private static final String LAST_NAME = "Sheremet";
    private static final BigDecimal SUBTOTAL = new BigDecimal(500.5);
    private static final BigDecimal DELIVERY_PRICE = new BigDecimal(5.5);
    private static final BigDecimal TOTAL_PRICE = new BigDecimal(505.5);
    private static final String ADDRESS = "Minsk";
    private static final String ADDITIONAL_INFO = "I am Vlad";
    private static final String CONTACT_PHONE_NO = "375291596658";
    private static final OrderStatus ORDER_STATUS = OrderStatus.NEW;
    private static final String ORDER_TABLE = "orders";
    private static final ArrayList<OrderItem> ORDER_ITEMS = new ArrayList<>();

    @Resource
    private OrderDao orderDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
        order.setId(ID);
        order.setSecureId(SECURE_ID);
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setSubtotal(SUBTOTAL);
        order.setDeliveryPrice(DELIVERY_PRICE);
        order.setTotalPrice(TOTAL_PRICE);
        order.setDeliveryAddress(ADDRESS);
        order.setAdditionalInfo(ADDITIONAL_INFO);
        order.setContactPhoneNo(CONTACT_PHONE_NO);
        order.setStatus(ORDER_STATUS);
        order.setOrderItems(ORDER_ITEMS);
    }

    @Test
    public void shouldReturnOrder() {
        Order actualOrder = orderDao.findOrder(SECURE_ID);

        assertEquals(order, actualOrder);
    }

    @Test
    public void shouldReturnOrders() {
        int expectedSize = 2;

        int actualSize = orderDao.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void shouldReturnIdToOrder() {
        Long expectedId = 1L;

        Long actualId = orderDao.findIdToOrder(order.getSecureId());

        assertEquals(expectedId, actualId);
    }

    @Test
    public void shouldSaveOrder() {
        int expectedSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_TABLE) + 1;

        orderDao.save(order);
        int actualSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_TABLE);

        assertEquals(expectedSize, actualSize);
    }
}