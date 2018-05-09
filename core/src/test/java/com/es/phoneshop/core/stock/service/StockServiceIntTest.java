package com.es.phoneshop.core.stock.service;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@Transactional
public class StockServiceIntTest {
    @Resource
    private StockService stockService;

    private static final Long EXISTING_PHONE_ID = 1001L;
    private static final Long EXISTING_PHONE_STOCK = 11L;
    private static final Long EXISTING_PHONE_RESERVED = 0L;
    private static final Long PHONE_ID_WITH_NO_STOCK = 5L;

    @Test
    public void testGetStock() {
        Phone phone = new Phone();
        phone.setId(EXISTING_PHONE_ID);
        Optional<Stock> stockOptional = stockService.getStock(phone);
        assertTrue(stockOptional.isPresent());
        Stock stock = stockOptional.get();
        assertEquals(EXISTING_PHONE_STOCK, stock.getStock());
        assertEquals(EXISTING_PHONE_RESERVED, stock.getReserved());
        assertSame(phone, stock.getPhone());
    }

    @Test
    public void testGetStockNotPresent() {
        Phone phone = new Phone();
        phone.setId(PHONE_ID_WITH_NO_STOCK);
        assertFalse(stockService.getStock(phone).isPresent());
    }

    @Test
    public void testUpdate() {
        Phone phone = new Phone();
        phone.setId(EXISTING_PHONE_ID);
        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(15L);
        stock.setReserved(5L);
        stockService.update(stock);
        Optional<Stock> stockOptional = stockService.getStock(phone);
        assertTrue(stockOptional.isPresent());
        stock = stockOptional.get();
        assertEquals((Long) 15L, stock.getStock());
        assertEquals((Long) 5L, stock.getReserved());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNotPresent() {
        Phone phone = new Phone();
        phone.setId(PHONE_ID_WITH_NO_STOCK);
        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(15L);
        stock.setReserved(5L);
        stockService.update(stock);
    }
}
