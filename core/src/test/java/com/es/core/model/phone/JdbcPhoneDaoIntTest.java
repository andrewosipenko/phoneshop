package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-config.xml")
public class JdbcPhoneDaoIntTest {

    @Resource
    private PhoneDao phoneDao;

    private static final long NON_EXISTENT_ID = 10000;

    private static final long ID_WITH_ONE_COLOR = 1001;

    private static final long ID_WITH_TEN_COLOR = 1002;

    private static final String PRODUCT_MODEL = "AT&T Avail";

    @Test
    public void checkProductWhichIsNotInDatabase() {
        String errorMessage = String.format("Product with id = %d exist in database", NON_EXISTENT_ID);
        assertFalse(errorMessage, phoneDao.get(NON_EXISTENT_ID).isPresent());
    }

    @Test
    public void checkProductWithOneColor() {
        assertEquals(1, phoneDao.get(ID_WITH_ONE_COLOR).get().getColors().size());
    }

    @Test
    public void checkProductWithMoreThenOneColor() {
        assertEquals(10, phoneDao.get(ID_WITH_TEN_COLOR).get().getColors().size());
    }

    @Test
    public void findAllProductWithLimit10() {
        assertEquals(10, phoneDao.findAll(0, 10).size());
    }

    @Test
    public void findAllInOrderWithLimit10() {
        assertEquals(10, phoneDao.findAllInOrder("brand", 0, 10).size());
    }

    @Test
    public void findByModelOneProduct() {
        assertEquals(1, phoneDao.findByModelInOrder(PRODUCT_MODEL, "brand", 0, 10).size());
    }

    @Test
    public void productCountTest() {
        assertEquals(20, phoneDao.productsCount());
    }

    @Test
    public void productWithModelCountTest() {
        assertEquals(1, phoneDao.productsCountWithModel(PRODUCT_MODEL));
    }

}
