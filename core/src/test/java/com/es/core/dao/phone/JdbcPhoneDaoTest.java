package com.es.core.dao.phone;


import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcPhoneDaoTest {

    @Resource
    private JdbcPhoneDao jdbcPhoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldReturnEmptyPhone() {
        Phone expectedPhone = new Phone();
        Phone actualPhone = jdbcPhoneDao.get(111111L).orElse(new Phone());

        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldReturnPhoneWithoutColors() {
        Phone expectedPhone = createPhone(1003L, "ARCHOS", "3", new HashSet<>());
        Phone actualPhone = jdbcPhoneDao.get(1003L).orElseThrow(IllegalArgumentException::new);

        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldReturnPhone() {
        Set<Color> colors = new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                new Color(1003L, "Blue")));

        Phone expectedPhone = createPhone(1000L, "ARCHOS", "0", colors);
        Phone actualPhone = jdbcPhoneDao.get(1000L).orElseThrow(IllegalArgumentException::new);

        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldUpdateOsToPhone() {
        Phone expectedPhone = createPhone(1003L, "ARCHOS", "3", new HashSet<>());
        expectedPhone.setOs("Android");
        jdbcPhoneDao.save(expectedPhone);

        Phone actualPhone = jdbcPhoneDao.get(expectedPhone.getId()).orElseThrow(IllegalArgumentException::new);
        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldUpdatePhone() {
        int expectedCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "phones") + 1;

        Phone phone = createPhone(1003L, "ARCHOS", "new model", new HashSet<>());
        jdbcPhoneDao.save(phone);

        int actualCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "phones");

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldUpdatePhoneWithNewColor() {
        Set<Color> colors = new HashSet<>(Arrays.asList(new Color(1004L, "Red"),
                new Color(1002L, "Yellow")));

        Phone expectedPhone = createPhone(1000L, "ARCHOS", "0", colors);
        jdbcPhoneDao.save(expectedPhone);
        Phone actualPhone = jdbcPhoneDao.get(1000L).orElseThrow(IllegalArgumentException::new);
        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldUpdatePhoneWithNewAndOldColor() {
        Set<Color> colors = new HashSet<>(Arrays.asList(new Color(1004L, "Red"),
                new Color(1002L, "Yellow"),
                new Color(1000L, "Black"),
                new Color(1003L, "Blue")));

        Phone expectedPhone = createPhone(1000L, "ARCHOS", "0", colors);

        jdbcPhoneDao.save(expectedPhone);
        Phone actualPhone = jdbcPhoneDao.get(1000L).orElseThrow(IllegalArgumentException::new);

        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldUpdatePhoneWithoutColor() {
        Phone expectedPhone = createPhone(1000L, "ARCHOS", "0", new HashSet<>());

        jdbcPhoneDao.save(expectedPhone);
        Phone actualPhone = jdbcPhoneDao.get(1000L).orElseThrow(IllegalArgumentException::new);

        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldSaveNewPhone() {
        int expectedCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "phones") + 1;

        Phone phone = createPhone(null, "ARCHOS", "5", new HashSet<>());

        jdbcPhoneDao.save(phone);
        int actualCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "phones");

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnAllPhones() {
        int expectedCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "phones");
        int actualCount = jdbcPhoneDao.findAll(0, 100).size();

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnAllPhonesWithLimit() {
        int expectedCount = 2;
        int actualCount = jdbcPhoneDao.findAll(0, 2).size();

        Assert.assertEquals(expectedCount, actualCount);
    }

    private Phone createPhone(Long id, String brand, String model, Set<Color> colors) {
        Phone phone = new Phone();
        phone.setColors(colors);
        phone.setId(id);
        phone.setModel(model);
        phone.setBrand(brand);

        return phone;
    }

}