package com.es.core.dao.stock;

import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.util.PhoneCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StockDaoImplTest {
    private static final long ID = 1003L;
    private static final String BRAND = "ARCHOS";
    private static final String MODEL = "3";
    private static final Long RESERVED = 2L;
    private static final Long QUANTITY = 13L;
    private static final Long NEW_QUANTITY = 10L;

    @Resource
    private StockDao stockDao;

    private Stock expectedStock;

    @Before
    public void setUp() {
        Phone phone = PhoneCreator.createPhone(ID, BRAND, MODEL, new HashSet<>());
        expectedStock = new Stock();
        expectedStock.setPhone(phone);
        expectedStock.setStock(QUANTITY);
        expectedStock.setReserved(RESERVED);
    }

    @Test
    public void shouldReturnPhoneStock() {
        Long actualQuantity = stockDao.findPhoneStock(ID).getStock();

        assertEquals(QUANTITY, actualQuantity);
    }

    @Test
    public void shouldUpdateQuantity() {
        stockDao.updateStock(ID, NEW_QUANTITY, RESERVED);
        Long actualQuantity = stockDao.findPhoneStock(ID).getStock();

        assertEquals(NEW_QUANTITY, actualQuantity);
    }
}