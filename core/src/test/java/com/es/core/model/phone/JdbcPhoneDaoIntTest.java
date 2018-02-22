package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class JdbcPhoneDaoIntTest {

    @Resource
    private PhoneDao phoneDao;

    private static final long NON_EXISTENT_ID = 10000;
    private static final long ID_WITH_ONE_COLOR = 1001;
    private static final long ID_WITH_TEN_COLOR = 1009;
    private static final String DEFAULT_ORDER = "brand_asc";
    private static final String PRODUCT_MODEL = "AT&T Avail 2";
    private static final String PRODUCT_NO_IN_DB = "no such phone";

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
        assertEquals(2, phoneDao.get(ID_WITH_TEN_COLOR).get().getColors().size());
    }

    @Test
    public void checkFindByAllModels() {
        assertEquals(11, phoneDao.findByModelInOrder("%", DEFAULT_ORDER, 0, 100).size());
    }

    @Test
    public void checkFindByModelOneProductWithTwoColor() {
        assertEquals(2, phoneDao.findByModelInOrder(PRODUCT_MODEL, DEFAULT_ORDER, 0, 100).size());
    }

    @Test
    public void checkOrderByPriceAsc() {
        List<Phone> phones = phoneDao.findByModelInOrder("%", "price_asc", 0, 10);
        for (int i = 0; i < phones.size() - 1; i++) {
            assertTrue(phones.get(i).getPrice().doubleValue() <= phones.get(i + 1).getPrice().doubleValue());
        }
    }

    @Test
    public void checkOrderByColorDesc() {
        List<Phone> phones = phoneDao.findByModelInOrder("%", "code_desc", 0, 10);
        for (int i = 0; i < phones.size() - 1; i++) {
            assertTrue(phones.get(i).getColors().toArray(new Color[]{})[0].getCode().compareTo(
                       phones.get(i).getColors().toArray(new Color[]{})[0].getCode()) >= 0);
        }
    }

    @Test
    public void checkProductCountWithoutModel() {
        assertEquals(11, phoneDao.productsCountWithModel("%"));
    }

    @Test
    public void checkProductCountWithModelAndTwoColor() {
        assertEquals(2, phoneDao.productsCountWithModel(PRODUCT_MODEL));
    }

    @Test
    public void checkProductCountWithModelWhichNoInDB() {
        assertEquals(0, phoneDao.productsCountWithModel(PRODUCT_NO_IN_DB));
    }
}
