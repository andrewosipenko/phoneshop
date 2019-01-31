package com.es.core.model.phone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-test.xml")
public class JdbcColorDaoTest {

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private ColorDao colorDao;

    private static long NOT_EXISTING_PHONE_ID = 12511L;
    private static long BLACK_COLOR_ID = 1000L;
    private static String BLACK_COLOR = "Black";

    private static String PINK_COLOR = "PINK KSENIA =)-";


    @Test
    public void testGetPhoneColors() {

    }

    @Test
    public void saveColor() {
        Color pinkColor = new Color();
        pinkColor.setCode(PINK_COLOR);

        colorDao.saveColor(pinkColor);

        Assert.assertTrue(pinkColor.getId() > 0);
    }

    @Test
    public void testGetExistingColor() {
        Color blackColor =
                colorDao.get(BLACK_COLOR_ID).get();

        Assert.assertTrue(blackColor.getCode().equals(BLACK_COLOR));
    }

    @Test
    public void testNotExistingColor() {
        Optional<Color> blackColor =
                colorDao.get(NOT_EXISTING_PHONE_ID);

        Assert.assertFalse(blackColor.isPresent());
    }

}

