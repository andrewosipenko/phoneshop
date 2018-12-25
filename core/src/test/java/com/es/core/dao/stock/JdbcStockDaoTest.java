package com.es.core.dao.stock;

import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.stock.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class JdbcStockDaoTest {
    private final Long PHONE_ID_WITH_EXISTING_STOCK = 1001L;
    private final Long PHONE_ID_WITH_NOT_EXISTING_STOCK = 1010L;
    private final Long STOCK = 11L;
    private final Long RESERVED = 0L;

    @Resource
    private StockDao stockDao;

    @Test
    public void shouldGetStockByPhoneId(){
        Stock stock = stockDao.get(PHONE_ID_WITH_EXISTING_STOCK).get();

        Assert.assertEquals(stock.getPhone().getId(), PHONE_ID_WITH_EXISTING_STOCK);
        Assert.assertEquals(stock.getStock(),STOCK);
        Assert.assertEquals(stock.getReserved(), RESERVED);
    }

    @Test(expected = OutOfStockException.class)
    public void shouldThrowOutOfStockExceptionGetStock(){
        stockDao.get(PHONE_ID_WITH_NOT_EXISTING_STOCK);
    }

    @Test
    public void shouldUpdateStock(){

    }
}
