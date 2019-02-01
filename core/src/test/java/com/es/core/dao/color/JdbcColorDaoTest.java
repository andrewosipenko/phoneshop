package com.es.core.dao.color;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.util.PhoneCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcColorDaoTest {
    private final static String BRAND = "ARCHOS";
    private final static Long BLACK_COLOR_ID = 1000L;
    private final static Long BLUE_COLOR_ID = 1003L;
    private final static Long RED_COLOR_ID = 1004L;
    private final static Long YELLOW_COLOR_ID = 1002L;
    private final static Long PHONE_ID_WITH_TWO_COLORS = 1000L;

    @Resource
    private ColorDao colorDao;

    @Test
    public void shouldReturnColorToPhone() {
        Set<Color> expectedColors = new HashSet<>(Arrays.asList(new Color(BLACK_COLOR_ID, "Black"),
                new Color(BLUE_COLOR_ID, "Blue")));

        Set<Color> actualColors = colorDao.findColorsToPhone(PHONE_ID_WITH_TWO_COLORS);

        assertEquals(expectedColors, actualColors);
    }

    @Test
    public void shouldUpdatePhoneColors() {
        Set<Color> expectedColors = new HashSet<>(Arrays.asList(new Color(RED_COLOR_ID, "Red"),
                new Color(YELLOW_COLOR_ID, "Yellow")));
        Phone phone = PhoneCreator.createPhone(1000L, BRAND, "0", expectedColors);

        colorDao.savePhoneColors(phone);
        Set<Color> actualColors = colorDao.findColorsToPhone(phone.getId());

        assertEquals(expectedColors, actualColors);
    }

    @Test
    public void shouldUpdateColorsToPhoneWithNewAndOldColor() {
        Set<Color> expectedColors = new HashSet<>(Arrays.asList(new Color(RED_COLOR_ID, "Red"),
                new Color(YELLOW_COLOR_ID, "Yellow"),
                new Color(BLACK_COLOR_ID, "Black"),
                new Color(BLUE_COLOR_ID, "Blue")));
        Phone phone = PhoneCreator.createPhone(PHONE_ID_WITH_TWO_COLORS, BRAND, "0", expectedColors);

        colorDao.savePhoneColors(phone);
        Set<Color> actualColor = colorDao.findColorsToPhone(phone.getId());

        assertEquals(expectedColors, actualColor);
    }
}
