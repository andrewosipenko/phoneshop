package com.es.core.model.stock;

import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/integrationTestContext.xml")
@TestPropertySource(properties = {"db.testSchemaSource=db/jdbcStockDaoIntTest-schemaSource.sql"})
public class JdbcStockDaoIntTest {

    private Stock createMockStock(long phoneId, int stock, int reserved){
        Stock mockStock = mock(Stock.class);
        when(mockStock.getReserved()).thenReturn(reserved);
        when(mockStock.getStock()).thenReturn(stock);
        Phone mockPhone = mock(Phone.class);
        when(mockPhone.getId()).thenReturn(phoneId);
        when(mockStock.getPhone()).thenReturn(mockPhone);
        return mockStock;
    }

    @Resource
    private JdbcStockDao jdbcStockDao;

    @Test
    @Transactional
    @Sql(statements = {
            "insert into stocks (phoneId, stock, reserved) values (1, 2, 1);" ,
            "insert into stocks (phoneId, stock, reserved) values (1001, 2, 0);",
            "insert into stocks (phoneId, stock, reserved) values (2, 3, 2);",
            "insert into stocks (phoneId, stock, reserved) values (345, 0, 0);"})
    public void getStocksForPhonesTestOnPhonesNotEmpty(){
        long phoneIds[] = {1, 1001, 2};
        int stocks[] = {2, 2, 3};
        int reserved[] = {1, 0, 2};

        Map<Long, Stock> expected = new HashMap<>();
        for(int i = 0; i < 3; i++){
            expected.put(phoneIds[i], createMockStock(phoneIds[i], stocks[i], reserved[i]));
        }

        List<Phone> phones = Arrays.stream(phoneIds)
                .mapToObj((id) -> {
                    Phone phone = mock(Phone.class);
                    when(phone.getId()).thenReturn(id);
                    return phone;
                })
                .collect(Collectors.toList());

        Map<Long, Stock> actual = jdbcStockDao.getStocksForPhones(phones);
        actual.forEach((key, actualValue)->{
            Assert.assertEquals(actualValue.getPhone().getId(), expected.get(key).getPhone().getId());
            Assert.assertEquals(actualValue.getReserved(), expected.get(key).getReserved());
            Assert.assertEquals(actualValue.getStock(), expected.get(key).getStock());
        });
    }

    @Test
    @Transactional
    @Sql(statements = {
            "insert into stocks (phoneId, stock, reserved) values (1, 2, 1);" ,
            "insert into stocks (phoneId, stock, reserved) values (1001, 2, 0);",
            "insert into stocks (phoneId, stock, reserved) values (2, 3, 2);",
            "insert into stocks (phoneId, stock, reserved) values (345, 0, 0);"})
    public void getStocksForPhonesTestOnEmptyPhones(){
        List<Phone> phones = Collections.emptyList();

        Map<Long, Stock> stocks = jdbcStockDao.getStocksForPhones(phones);
        int actual = stocks.size();
        int expected = 0;
        Assert.assertEquals(expected, actual);
    }
}
