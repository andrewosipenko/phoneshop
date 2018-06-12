package com.es.core.model.stock;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import jdk.nashorn.api.scripting.ScriptUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/integrationTestContext.xml")
@TestPropertySource(properties = {"db.testSchemaSource=db/jdbcStockDaoIntTest-schemaSource.sql"})
public class JdbcStockDaoIntTest {

    @Resource
    private JdbcStockDao jdbcStockDao;

    @Test
    @Transactional
    public void getStocksForPhonesTestOnPhonesNotEmpty(){
        long phoneIds[] = {1, 1001, 2};
        int stocks[] = {2, 2, 3};
        int reserved[] = {1, 0, 2};

        Map<Phone, Stock> expected = new HashMap<>();
        Phone mockPhone;
        for(int i = 0; i < 3; i++){
            mockPhone = createMockPhoneWithId(phoneIds[i]);
            expected.put(mockPhone, createMockStock(mockPhone, stocks[i], reserved[i]));
        }

        List<Phone> phones = expected.values().stream().map(Stock::getPhone).collect(Collectors.toList());

        Map<Phone, Stock> actual = jdbcStockDao.getStocksForPhones(phones);
        actual.forEach((key, actualValue)->{
            Assert.assertEquals(actualValue.getReserved(), expected.get(key).getReserved());
            Assert.assertEquals(actualValue.getStock(), expected.get(key).getStock());
        });
    }

    @Test
    @Transactional
    public void getStocksForPhonesTestOnEmptyPhones(){
        List<Phone> phones = Collections.emptyList();

        Map<Phone, Stock> stocks = jdbcStockDao.getStocksForPhones(phones);
        int actual = stocks.size();
        int expected = 0;
        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void decreaseStockOnNotEmptyItems(){
        long[] stock = {2, 2, 3};
        long[] quantities = {1, 1, 1};
        long[] phoneIds = {1, 1001, 2};

        long[] expected = {0, 0, 0};
        for(int i = 0; i < 3; i++){
            expected[i] = stock[i] - quantities[i];
        }

        Order order = createMockOrder(phoneIds, quantities);

        jdbcStockDao.decreaseStocks(order);

        List<Phone> phones = order.getOrderItems().stream().map(OrderItem::getPhone).collect(Collectors.toList());
        Map<Phone, Stock> stocks = jdbcStockDao.getStocksForPhones(phones);
        long[] actual = stocks.values().stream().mapToLong(Stock::getStock).sorted().toArray();

        Assert.assertArrayEquals(expected, actual);
    }

    private Order createMockOrder(long[] phoneIds, long[] quantities){
        Order order = mock(Order.class);
        List<OrderItem> orderItems = new LinkedList<>();
        for(int i = 0; i < 3; i++){
            orderItems.add(createMockOrderItem(phoneIds[i], quantities[i]));
        }
        when(order.getOrderItems()).thenReturn(orderItems);
        return order;
    }

    private Stock createMockStock(Phone mockPhone, int stock, int reserved){
        Stock mockStock = mock(Stock.class);
        when(mockStock.getReserved()).thenReturn(reserved);
        when(mockStock.getStock()).thenReturn(stock);
        when(mockStock.getPhone()).thenReturn(mockPhone);
        return mockStock;
    }

    private Phone createMockPhoneWithId(long phoneId){
        Phone phone = mock(Phone.class);
        when(phone.getId()).thenReturn(phoneId);
        return phone;
    }

    private OrderItem createMockOrderItem(long phoneId, long quantity){
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.getQuantity()).thenReturn(quantity);
        Phone phone = createMockPhoneWithId(phoneId);
        when(orderItem.getPhone()).thenReturn(phone);
        return orderItem;
    }
}
