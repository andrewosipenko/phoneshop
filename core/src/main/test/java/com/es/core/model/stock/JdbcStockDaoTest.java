package com.es.core.model.stock;

import com.es.core.exception.PhoneNotFindException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcStockDaoTest {
    public static final long EXIST_PHONE = 1000L;
    public static final long NOT_EXIST_PHONE = 1L;
    public static final int ACTUAL_RESERVED = 0;
    public static final int ACTUAL_STOCK = 11;

    @Resource
    private StockDao stockDao;

    @Test
    public void shouldStockDaoExistMethod() {
        assertNotNull(stockDao);
    }

    @Test
    public void shouldGetStockByIdMethod() {
        int expectedStock = stockDao.getStock(EXIST_PHONE);
        Assert.assertEquals(expectedStock, ACTUAL_STOCK);
    }

    @Test(expected = PhoneNotFindException.class)
    public void shouldThrowExceptionWhenPhoneIdIsUnavailableGetStockMethod() {
        stockDao.getStock(NOT_EXIST_PHONE);
        Assert.fail();
    }

    @Test
    public void shouldGetReservedByIdMethod() {
        int expectedReserved = stockDao.getReserved(EXIST_PHONE);
        Assert.assertEquals(expectedReserved, ACTUAL_RESERVED);
    }

    @Test(expected = PhoneNotFindException.class)
    public void shouldThrowExceptionWhenPhoneIdIsUnavailableGetReservedMethod() {
        stockDao.getReserved(NOT_EXIST_PHONE);
        Assert.fail();
    }
}
