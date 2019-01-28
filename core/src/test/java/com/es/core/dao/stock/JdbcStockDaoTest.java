package com.es.core.dao.stock;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class JdbcStockDaoTest {
    private final Long PHONE_ID_WITH_EXISTING_STOCK = 1001L;
    private final Long PHONE_ID_WITH_NOT_EXISTING_STOCK = 999L;
    private final Integer STOCK = 11;
    private final Integer RESERVED = 0;

    @Resource
    private StockDao stockDao;
    @Resource
    private PhoneDao phoneDao;

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
        Phone phone = phoneDao.get(PHONE_ID_WITH_EXISTING_STOCK).get();
        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(0L);
        stock.setReserved(0L);
        List<Stock> stockList = new ArrayList<>();
        stockList.add(stock);

        stockDao.update(stockList);
        Stock newStock = stockDao.get(PHONE_ID_WITH_EXISTING_STOCK).get();

        Assert.assertTrue(newStock.getStock() == 0);
        Assert.assertTrue(newStock.getReserved() == 0);
    }
}
