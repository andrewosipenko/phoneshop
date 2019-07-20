package com.es.core.model;

import com.es.core.model.phone.Color;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:context/applicationContext-test-*.xml"})
public class JdbcColorDaoIntTest {

    @Resource
    private JdbcColorDao jdbcColorDao;

    @Test
    public void testLoadColorsOfPhoneByIDWithID_1101() {
        Set<Color> actual = jdbcColorDao.loadColorsOfPhoneByID(1101L);
        Set<Color> expected = getColorsOfPhoneWithID_1101();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testLoadColorsOfPhoneByIDWithID_1744() {
        Set<Color> actual = jdbcColorDao.loadColorsOfPhoneByID(1744L);
        Set<Color> expected = getColorsOfPhoneWithID_1744();
        Assert.assertEquals(actual, expected);
    }

    private Set<Color> getColorsOfPhoneWithID_1101() {
        return Stream.of(new Color(1000L, "Black")).collect(Collectors.toSet());
    }

    private Set<Color> getColorsOfPhoneWithID_1744() {
        return Stream.of(new Color(1000L, "Black"),
                new Color(1001L, "White"), new Color(1003L, "Blue"),
                new Color(1006L, "Gray"), new Color(1008L, "Pink"),
                new Color(1009L, "Gold")).collect(Collectors.toSet());
    }
}
