package com.es.core.dao.stockDao;

import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.exception.NoSuchStockException;
import com.es.core.service.stock.StockService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcStockDaoIntTest {
    @Resource
    private JdbcStockDao stockDao;
    @Resource
    private StockService stockService;

    private Long PHONE_ID_WITH_EXISTING_STOCK = 1003L;
    private Long PHONE_ID_WITH_NOT_EXISTING_STOCK = 1010L;
    private Long STOCK = 13L;
    private Long RESERVED = 2L;

    @Test
    public void updateStocks(){
        Phone phone = new Phone();
        phone.setId(PHONE_ID_WITH_EXISTING_STOCK);

        List<Stock> stockList = stockService.getPhonesStocks(Arrays.asList(phone));
        Stock stock = stockList.get(0);
        stock.setStock(0L);
        stock.setReserved(0L);

        stockDao.updateStocks(Arrays.asList(stock));
        stockList = stockService.getPhonesStocks(Arrays.asList(phone));
        stock = stockList.get(0);

        Assert.assertTrue(stock.getStock() == 0);
        Assert.assertTrue(stock.getReserved() == 0);
        Assert.assertTrue(stock.getPhone().getId().equals(PHONE_ID_WITH_EXISTING_STOCK));
    }

    @Test
    public void getStockByValidPhoneId(){
        Stock stock = stockDao.getStockByPhoneId(PHONE_ID_WITH_EXISTING_STOCK);
        Assert.assertTrue(stock.getPhone().getId().equals(PHONE_ID_WITH_EXISTING_STOCK));
        Assert.assertTrue(stock.getStock().equals(STOCK));
        Assert.assertTrue(stock.getReserved().equals(RESERVED));
    }

    @Test(expected = NoSuchStockException.class)
    public void getStockByInvalidPhoneId(){
        stockDao.getStockByPhoneId(PHONE_ID_WITH_NOT_EXISTING_STOCK);
    }

}