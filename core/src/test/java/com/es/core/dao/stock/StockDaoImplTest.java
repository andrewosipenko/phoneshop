package com.es.core.dao.stock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StockDaoImplTest {
    @Resource
    private StockDao stockDao;

    @Test
    public void shouldReturnPhoneQuantity() {
        Long expectedStock = 13L;

        Long actualStock = stockDao.findPhoneQuantity(1003L);

        assertEquals(expectedStock, actualStock);
    }
}