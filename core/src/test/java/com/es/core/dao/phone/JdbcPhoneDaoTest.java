package com.es.core.dao.phone;


import com.es.core.model.phone.Phone;
import com.es.core.util.PhoneCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcPhoneDaoTest {
    private final static String BRAND = "ARCHOS";
    private final static Long PHONE_ID_WITHOUT_COLOR = 1003L;
    private final static String PHONE_TABLE = "phones";

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldReturnEmptyPhone() {
        Phone expectedPhone = new Phone();

        Phone actualPhone = phoneDao.get(111111L).orElse(new Phone());

        assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldUpdateOsToPhone() {
        Phone expectedPhone = PhoneCreator.createPhone(PHONE_ID_WITHOUT_COLOR, BRAND, "3", new HashSet<>());
        expectedPhone.setOs("Android");

        phoneDao.save(expectedPhone);
        Phone actualPhone = phoneDao.get(expectedPhone.getId()).orElseThrow(IllegalArgumentException::new);

        assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldUpdatePhone() {
        int expectedCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, PHONE_TABLE) + 1;
        Phone phone = PhoneCreator.createPhone(PHONE_ID_WITHOUT_COLOR, BRAND, "new model", new HashSet<>());

        phoneDao.save(phone);
        int actualCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, PHONE_TABLE);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldSaveNewPhone() {
        int expectedCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, PHONE_TABLE) + 1;
        Phone phone = PhoneCreator.createPhone(null, BRAND, "5", new HashSet<>());

        phoneDao.save(phone);
        int actualCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, PHONE_TABLE);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnAllPhones() {
        int expectedCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, PHONE_TABLE);

        int actualCount = phoneDao.findAll(0, 100).size();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnAllPhonesWithLimit() {
        int expectedCount = 2;

        int actualCount = phoneDao.findAll(0, 2).size();

        assertEquals(expectedCount, actualCount);
    }
}