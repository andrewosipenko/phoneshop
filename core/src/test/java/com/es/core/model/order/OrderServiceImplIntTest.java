package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import com.es.core.model.stock.JdbcStockDao;
import com.es.core.model.stock.StockDao;
import com.es.core.order.OrderService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/integrationTestContext.xml")
@TestPropertySource(properties = {"db.testSchemaSource=db/orderServiceImplIntTest-schemaSource.sql"})
public class OrderServiceImplIntTest {

    @Resource
    private OrderService orderService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ApplicationContext applicationContext;

    private static Order mockOrder;

    @BeforeClass
    public static void beforeTests(){
        mockOrder = createOrder();
    }

    private static OrderItem createOrderItem(Order order){
        OrderItem item = new OrderItem();
        item.setQuantity(1L);
        item.setOrder(order);

        Phone phone = new Phone();
        phone.setId(1001L);

        item.setPhone(phone);
        return item;
    }

    private static Order createOrder(){
        Order order = new Order();
        order.setId(0L);
        order.setOrderUUID(UUID.randomUUID());
        order.setStatus(OrderStatus.NEW);
        order.setFirstName("fname");
        order.setLastName("lname");
        order.setDeliveryAddress("address");
        order.setContactPhoneNo("123123123");
        order.setSubtotal(BigDecimal.ZERO);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setDeliveryPrice(BigDecimal.ZERO);

        List<OrderItem> items = Arrays.asList(
                createOrderItem(order)
        );
        order.setOrderItems(items);
        return order;
    }

    @Test
    public void placeOrderTransactionRollbackOnException(){
        StockDao stockDao = mock(JdbcStockDao.class);
        doThrow(new RuntimeException()).when(stockDao).reserveStocks(anyObject());
        ReflectionTestUtils.setField(orderService, "stockDao", stockDao);


        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders");

        try {
            orderService.placeOrder(mockOrder);
        } catch(RuntimeException e){
            System.err.println("RuntimeException");
        }

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders");

        StockDao originalStockDao = applicationContext.getBean(JdbcStockDao.class);
        ReflectionTestUtils.setField(orderService, "stockDao", originalStockDao);

        assertEquals(expected, actual);
    }

    @Test
    public void placeOrderTransactionNoRollback(){
        int itemsAmount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders");
        int itemsAmountToAdd = mockOrder.getOrderItems().size();

        int expected =  itemsAmount + itemsAmountToAdd;

        orderService.placeOrder(mockOrder);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders");

        assertEquals(expected, actual);
    }
}
