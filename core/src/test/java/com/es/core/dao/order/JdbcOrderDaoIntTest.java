package com.es.core.dao.order;

import com.es.core.AbstractTest;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration("classpath:context/testContext-core.xml")
public class JdbcOrderDaoIntTest extends AbstractTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private OrderDao orderDao;

    private static final Long EXIST_ORDER_ID = 1001L;

    private static final String ORDERS_TABLE_NAME = "orders";

    private static final String ORDER_ITEMS_TABLE_NAME = "orderItems";

    @Before
    public void init() {
        final Long STOCK_INIT_PHONES = 999L;
        phoneList = addNewPhones(5);
        setStock(STOCK_INIT_PHONES);
    }

    @Test
    public void insert() throws OutOfStockException {
        Order order = createOrder(null, 3);
        order.setStatus(OrderStatus.DELIVERED);
        orderDao.save(order);
        Order gettedOrder = orderDao.get(order.getId()).get();

        assertEquals(order.getDeliveryAddress(), gettedOrder.getDeliveryAddress());
        assertEquals(order.getContactPhoneNo(), gettedOrder.getContactPhoneNo());
        assertEquals(order.getFirstName(), gettedOrder.getFirstName());
        assertEquals(order.getLastName(), gettedOrder.getLastName());
        assertEquals(order.getAdditionalInfo(), gettedOrder.getAdditionalInfo());
        assertTrue(gettedOrder.getDeliveryPrice().compareTo(order.getDeliveryPrice()) == 0);
        assertTrue(gettedOrder.getTotalPrice().compareTo(order.getTotalPrice()) == 0);
        assertTrue(gettedOrder.getSubtotal().compareTo(order.getSubtotal()) == 0);
        assertEquals(order.getStatus(), order.getStatus());
        assertEquals(order.getOrderItems(), gettedOrder.getOrderItems());
    }

    @Test(expected = OutOfStockException.class)
    public void insertOutOfStock() throws OutOfStockException {
        setStock(0L);
        Order order = createOrder(null, 3);
        orderDao.save(order);
    }

    @Test
    public void getOrder() {
        Optional<Order> orderOptional = orderDao.get(EXIST_ORDER_ID);
        assertEquals(EXIST_ORDER_ID, orderOptional.get().getId());
    }

    @Test
    public void update() throws OutOfStockException {
        final String SAVED_DATA = "string for test";
        final BigDecimal SAVED_COST = new BigDecimal(1000);
        final OrderStatus SAVED_STATUS = OrderStatus.DELIVERED;

        Order order = orderDao.get(EXIST_ORDER_ID).get();

        order.setDeliveryAddress(SAVED_DATA);
        order.setContactPhoneNo(SAVED_DATA);
        order.setFirstName(SAVED_DATA);
        order.setLastName(SAVED_DATA);
        order.setAdditionalInfo(SAVED_DATA);
        order.setDeliveryPrice(SAVED_COST);
        order.setTotalPrice(SAVED_COST);
        order.setSubtotal(SAVED_COST);
        order.setStatus(SAVED_STATUS);

        orderDao.save(order);

        Order changedOrder = orderDao.get(EXIST_ORDER_ID).get();

        assertEquals(SAVED_DATA, changedOrder.getDeliveryAddress());
        assertEquals(SAVED_DATA, changedOrder.getContactPhoneNo());
        assertEquals(SAVED_DATA, changedOrder.getFirstName());
        assertEquals(SAVED_DATA, changedOrder.getLastName());
        assertEquals(SAVED_DATA, changedOrder.getAdditionalInfo());
        assertTrue(changedOrder.getDeliveryPrice().compareTo(SAVED_COST) == 0);
        assertTrue(changedOrder.getTotalPrice().compareTo(SAVED_COST) == 0);
        assertTrue(changedOrder.getSubtotal().compareTo(SAVED_COST) == 0);
        assertEquals(SAVED_STATUS, changedOrder.getStatus());
    }

    @Test
    public void updateSetRejected() throws OutOfStockException {
        final OrderStatus ORDER_STATUS = OrderStatus.REJECTED;

        Order order = orderDao.get(EXIST_ORDER_ID).get();

        order.setStatus(ORDER_STATUS);

        Long phoneId = order.getOrderItems().get(0).getPhone().getId();

        Long quantity = order.getOrderItems().get(0).getQuantity();

        Stock stock = getStockObject(phoneId);

        orderDao.save(order);

        Order changedOrder = orderDao.get(EXIST_ORDER_ID).get();

        Stock changedStock = getStockObject(phoneId);

        assertEquals(ORDER_STATUS, changedOrder.getStatus());
        assertEquals((long) stock.getReserved(), changedStock.getReserved() + quantity);
    }


    @Test
    public void updateSetDelivered() throws OutOfStockException {
        final OrderStatus ORDER_STATUS = OrderStatus.DELIVERED;

        Order order = orderDao.get(EXIST_ORDER_ID).get();

        order.setStatus(ORDER_STATUS);

        Long phoneId = order.getOrderItems().get(0).getPhone().getId();

        Long quantity = order.getOrderItems().get(0).getQuantity();

        Stock stock = getStockObject(phoneId);

        orderDao.save(order);

        Order changedOrder = orderDao.get(EXIST_ORDER_ID).get();

        Stock changedStock = getStockObject(phoneId);

        assertEquals(ORDER_STATUS, changedOrder.getStatus());
        assertEquals((long) stock.getReserved(), changedStock.getReserved() + quantity);
        assertEquals((long) stock.getStock(), changedStock.getStock() + quantity);
    }

    @Test
    public void updateOrderItems() throws OutOfStockException {
        final Long ADD_QUANTITY = 5L;
        Order order = orderDao.get(EXIST_ORDER_ID).get();

        OrderItem orderItem = order.getOrderItems().get(0);
        Long Quantity = orderItem.getQuantity() + ADD_QUANTITY;
        orderItem.setQuantity(Quantity);

        int stock = getStock(orderItem.getPhone().getId());

        orderDao.save(order);

        Order changedOrder = orderDao.get(EXIST_ORDER_ID).get();

        assertEquals(Quantity, order.getOrderItems().get(0).getQuantity());
        assertEquals((long) ADD_QUANTITY, (long) (stock - getStock(orderItem.getPhone().getId())));
    }

    @Test(expected = OutOfStockException.class)
    public void updateOrderItemsOutOfStock() throws OutOfStockException {
        final Long ADD_QUANTITY = 5L;
        Order order = orderDao.get(EXIST_ORDER_ID).get();

        OrderItem orderItem = order.getOrderItems().get(0);
        setStock(orderItem.getPhone().getId(), 0L);

        orderItem.setQuantity(orderItem.getQuantity() + ADD_QUANTITY);

        orderDao.save(order);
    }

    @Test
    public void updateWithDeleteOrderItems() throws OutOfStockException {
        Order order = orderDao.get(EXIST_ORDER_ID).get();

        OrderItem orderItem = order.getOrderItems().remove(0);
        int stock = getStock(orderItem.getPhone().getId());

        orderDao.save(order);
        assertEquals((long) stock + orderItem.getQuantity(), getStock(orderItem.getPhone().getId()));
    }

    @Test
    public void updateWithInsertOrderItems() throws OutOfStockException {
        final Long QUANTITY = 5L;
        Order order = orderDao.get(EXIST_ORDER_ID).get();

        Phone addedPhone = phoneList.get(0);

        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setQuantity(QUANTITY);
        newOrderItem.setOrder(order);
        newOrderItem.setPhone(addedPhone);

        int stock = getStock(addedPhone.getId());

        order.getOrderItems().add(newOrderItem);

        orderDao.save(order);
        assertEquals((long) QUANTITY, stock - getStock(addedPhone.getId()));
    }

    @Test(expected = OutOfStockException.class)
    public void updateWithInsertOrderItemsOutOfStock() throws OutOfStockException {
        final Long QUANTITY = 5L;
        Order order = orderDao.get(EXIST_ORDER_ID).get();

        Phone addedPhone = phoneList.get(0);

        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setQuantity(QUANTITY);
        newOrderItem.setOrder(order);
        newOrderItem.setPhone(addedPhone);
        setStock(addedPhone.getId(), 0L);

        order.getOrderItems().add(newOrderItem);

        orderDao.save(order);
    }

    @Test
    public void getNonexistentOrder() {
        final Long NONEXISTENT_ORDER_ID = Long.MAX_VALUE;
        Optional<Order> orderOptional = orderDao.get(NONEXISTENT_ORDER_ID);
        assertFalse(orderOptional.isPresent());
    }

    @Test
    public void getCountOrders() {
        final int COUNT_ORDERS = countRowsInTable(ORDERS_TABLE_NAME);
        assertEquals(COUNT_ORDERS, orderDao.orderCount());
    }

    @Test
    public void findAllCheck() {
        final int COUNT = 2;
        final int OFFSET = 0;
        List<Order> orderList = orderDao.findAll(OFFSET, COUNT);
        assertEquals(COUNT, orderList.size());
    }

    @Test
    public void findAllLargeOffset() {
        final int COUNT = 2;
        final int OFFSET = 1000_000_000;
        List<Order> orderList = orderDao.findAll(OFFSET, COUNT);
        assertEquals(0L, orderList.size());
    }

    @Test
    public void findAllLargeCount() {
        final int COUNT = 1000_000_000;
        final int OFFSET = 0;
        List<Order> orderList = orderDao.findAll(OFFSET, COUNT);
        assertTrue(orderList.size() < COUNT);
    }

    private void setStock(Long phoneId, Long stock) {
        Map<Long, Long> phoneStockMap = new HashMap<>();
        phoneStockMap.put(phoneId, stock);
        setStocks(phoneStockMap);
    }

    private void setStock(Long stock) {
        Map<Long, Long> phoneStockMap = phoneList.stream()
                .collect(Collectors.toMap(Phone::getId, t -> stock));
        setStocks(phoneStockMap);
    }

    private int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }

    private int getStock(Long phoneId) {
        return jdbcTemplate.queryForObject("select (stock-reserved) from stocks where phoneId = ?", Integer.class, phoneId);
    }

    private Stock getStockObject(Long phoneId) {
        return jdbcTemplate.queryForObject("select * from stocks where phoneId = ?",
                new BeanPropertyRowMapper<>(Stock.class), phoneId);
    }
}