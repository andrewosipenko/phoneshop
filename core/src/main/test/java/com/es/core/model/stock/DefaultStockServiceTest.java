package com.es.core.model.stock;

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
public class DefaultStockServiceTest {
    public static final long EXIST_PHONE = 1000L;
    public static final int ACTUAL_STOCK = 11;

    @Resource
    private StockService stockService;

    @Test
    public void shouldStockDaoExistMethod() {
        assertNotNull(stockService);
    }

    @Test
    public void shouldGetStockByIdMethod() {
        int expectedStock = stockService.getAvailablePhoneStock(EXIST_PHONE);
        Assert.assertEquals(expectedStock, ACTUAL_STOCK);
    }
}
