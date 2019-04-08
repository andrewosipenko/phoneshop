package com.es.core.dao.orderItem;

import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcOrderItemDaoImplTest {
    private static final String ORDER_ITEM_TABLE = "orderItems";
    private static final long PHONE_ID = 1000L;
    private static final long QUANTITY = 1L;
    private static final long ORDER_ID = 2L;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldSaveOrderItem() {
        int expectedSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE) + 1;
        Phone phone = new Phone();
        phone.setId(PHONE_ID);
        OrderItem orderItem = new OrderItem(phone, QUANTITY);

        orderItemDao.save(orderItem, ORDER_ID);
        int actualSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE);

        assertEquals(expectedSize, actualSize);
    }

}