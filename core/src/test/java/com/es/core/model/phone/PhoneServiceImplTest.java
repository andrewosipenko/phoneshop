package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class PhoneServiceImplTest {

    @Resource
    private PhoneService phoneService;

    private static final long NON_EXISTENT_ID = 10000;
    private static final long ID_WITH_ONE_COLOR = 1001;
    private static final long ID_WITH_TWO_COLOR = 1009;
    private static final String DEFAULT_ORDER = "brand_asc";
    private static final String PRODUCT_MODEL = "AT&T Avail 2";
    private static final String COLOR_IN_PHONE_1001 = "White";
    private static final String PART_OF_MODEL = "Samsung";

    @Test
    public void checkProductWhichIsNotInDatabase() {
        String errorMessage = String.format("Product with id = %d exist", NON_EXISTENT_ID);
        assertFalse(errorMessage, phoneService.get(NON_EXISTENT_ID).isPresent());
    }

    @Test
    public void checkProductWithOneColor() {
        assertEquals(1, phoneService.get(ID_WITH_ONE_COLOR).get().getColors().size());
    }

    @Test
    public void checkProductWithMoreThenOneColor() {
        assertEquals(2, phoneService.get(ID_WITH_TWO_COLOR).get().getColors().size());
    }

    @Test
    public void checkFindByAllModels() {
        assertEquals(11, phoneService.findInOrder(DEFAULT_ORDER, 0, 100).size());
    }

    @Test
    public void checkFindByModelOneProductWithTwoColor() {
        assertEquals(2, phoneService.findByModelInOrder(PRODUCT_MODEL, DEFAULT_ORDER, 0, 100).size());
    }

    @Test
    public void checkFindByPartModel() {
        assertEquals(1, phoneService.findByModelInOrder(PART_OF_MODEL, DEFAULT_ORDER, 0, 100).size());
    }

    @Test
    public void checkOrderByPriceDesc() {
        List<Phone> phones = phoneService.findInOrder("price_desc", 0, 10);
        for (int i = 0; i < phones.size() - 1; i++) {
            assertTrue(phones.get(i).getPrice().doubleValue() >= phones.get(i + 1).getPrice().doubleValue());
        }
    }

    @Test
    public void checkOrderByBrandAsc() {
        List<Phone> phones = phoneService.findInOrder("brand_asc", 0, 10);
        for (int i = 0; i < phones.size() - 1; i++) {
            assertTrue(phones.get(i).getColors().toArray(new Color[]{})[0].getCode().compareTo(
                    phones.get(i).getColors().toArray(new Color[]{})[0].getCode()) <= 0);
        }
    }

    @Test
    public void checkProductCountWithoutModel() {
        assertEquals(11, phoneService.productsCount());
    }

    @Test
    public void checkProductCountWithModelAndTwoColor() {
        assertEquals(2, phoneService.productsCountByModel(PRODUCT_MODEL));
    }

    @Test
    public void checkProductCountWithModelWhichNoInDB() {
        assertEquals(0, phoneService.productsCountByModel("no such phone"));
    }

    @Test
    public void checkProductCountWithPartOfModel() {
        assertEquals(1, phoneService.productsCountByModel(PART_OF_MODEL));
    }

    @Test
    public void checkGetPhoneByIdAndColor() {
        assertTrue(phoneService.getByIdAndColor(ID_WITH_ONE_COLOR, COLOR_IN_PHONE_1001).isPresent());
    }

    @Test
    public void checkGetPhoneByIdAndNoExistentColor() {
        assertFalse(phoneService.getByIdAndColor(ID_WITH_ONE_COLOR, "no existent color").isPresent());
    }

    @Test
    public void checkGetPhoneByNoExistentId() {
        assertFalse(phoneService.getByIdAndColor(NON_EXISTENT_ID, "Black").isPresent());
    }
}