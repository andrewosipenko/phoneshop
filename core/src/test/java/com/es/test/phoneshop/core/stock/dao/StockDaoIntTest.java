package com.es.test.phoneshop.core.stock.dao;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.dao.StockDao;
import com.es.phoneshop.core.stock.model.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@Transactional
public class StockDaoIntTest {
    @Resource
    private StockDao stockDao;

    private static final Long EXISTING_PHONE_A_ID = 1002L;
    private static final Integer PHONE_A_STOCK = 12;
    private static final Integer PHONE_A_RESERVED = 1;
    private static final Long UNEXISTING_PHONE_ID = 999L;

    @Test
    public void testGetNormal() {
        Phone phone = new Phone();
        phone.setId(EXISTING_PHONE_A_ID);
        Optional<Stock> stockOptional = stockDao.get(phone);
        assertTrue(stockOptional.isPresent());
        Stock stock = stockOptional.get();
        assertEquals(stock.getStock(), PHONE_A_STOCK);
        assertEquals(stock.getReserved(), PHONE_A_RESERVED);
    }

    @Test
    public void testGetUnexisting() {
        Phone phone = new Phone();
        phone.setId(UNEXISTING_PHONE_ID);
        Optional<Stock> stockOptional = stockDao.get(phone);
        assertFalse(stockOptional.isPresent());
    }
}
