package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testDB/test-config.xml")
public class JdbcPhoneDaoIntTest {

    @Resource
    private PhoneDao phoneDao;

    private static final long NON_EXISTENT_ID = 10000;

    private static final long ID_WITH_ONE_COLOR = 1001;

    private static final long ID_WITH_TEN_COLOR = 1002;

    @Test
    public void checkProductWhichIsNotInDatabase() {
        String errorMessage = String.format("Product with id = %d exist in database", NON_EXISTENT_ID);
        assertFalse(errorMessage, phoneDao.get(NON_EXISTENT_ID).isPresent());
    }

    @Test
    public void checkProductWithOneColor() {
        int colorsAmount = phoneDao.get(ID_WITH_ONE_COLOR).get().getColors().size();
        String errorMessage = String.format("Product with id = %d is not have 1 color (have %d)", ID_WITH_ONE_COLOR, colorsAmount);
        assertTrue(errorMessage, colorsAmount == 1);
    }

    @Test
    public void checkProductWithMoreThenOneColor() {
        int colorsAmount = phoneDao.get(ID_WITH_TEN_COLOR).get().getColors().size();
        String errorMessage = String.format("Product with id = %d is not have 10 color (have %d)", ID_WITH_TEN_COLOR, colorsAmount);
        assertTrue(errorMessage, colorsAmount == 10);
    }

}
