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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcPhoneDaoTest {
    private final static String BRAND = "ARCHOS";
    private final static Long PHONE_ID_WITHOUT_COLOR = 1003L;
    private final static String PHONE_TABLE = "phones";
    private final static int PAGE_SIZE = 10;
    private final static String MODEL_3 = "3";

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
        Phone expectedPhone = PhoneCreator.createPhone(PHONE_ID_WITHOUT_COLOR, BRAND, MODEL_3, new HashSet<>());
        expectedPhone.setOs("Android");
        expectedPhone.setWeightGr(0);
        expectedPhone.setPixelDensity(0);
        expectedPhone.setBatteryCapacityMah(0);

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

    @Test
    public void shouldReturnPhonesToFirstPage() {
        Phone phone = PhoneCreator.createPhone(PHONE_ID_WITHOUT_COLOR, BRAND, MODEL_3, new HashSet<>());
        PhoneCreator.setNumbersValueToPhone(phone);
        List<Phone> expectedList = new ArrayList<Phone>(Collections.singletonList(phone));

        List<Phone> actualList = phoneDao.findActivePhonesByPage(2,1);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldReturnPhonesWithCurrentModel() {
        Phone phone = PhoneCreator.createPhone(PHONE_ID_WITHOUT_COLOR, BRAND, MODEL_3, new HashSet<>());
        PhoneCreator.setNumbersValueToPhone(phone);
        List<Phone> expectedList = new ArrayList<Phone>(Collections.singletonList(phone));

        List<Phone> actualList = phoneDao.findPhonesLikeQuery(0, PAGE_SIZE, MODEL_3);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldReturnEmptyListWhenNoPhoneWithCurrentBrand() {
        List<Phone> expectedList = new ArrayList<>();

        List<Phone> actualList = phoneDao.findPhonesLikeQuery(0, PAGE_SIZE, "asvaf");

        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldReturnNumberOfPageOfPhonesWithCurrentBrand() {
        int expectedCount = 3;

        int actualCount = phoneDao.findPageCountWithQuery(1, BRAND);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnNumberOfPageOfActivePhones() {
        int expectedCount = 3;

        int actualCount = phoneDao.findPageCount(1);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnSortAscPhones() {
        boolean isSort = true;

        List<Phone> phones = phoneDao.sortPhones(0,10,"model", "asc");
        for (int i = 0; i < phones.size() - 1; i++) {
            if (phones.get(i).getModel().compareTo(phones.get(i + 1).getModel()) >= 1) {
                isSort = false;
                break;
            }
        }

        assertTrue(isSort);
    }

    @Test
    public void shouldReturnSortDescPhones() {
        boolean isSort = true;

        List<Phone> phones = phoneDao.sortPhones(0,10,"model", "desc");
        int size = phones.size() - 1;
        for (int i = 0; i < size; i++) {
            if (phones.get(i).getModel().compareTo(phones.get(i + 1).getModel()) < 1) {
                isSort = false;
                break;
            }
        }

        assertTrue(isSort);
    }

    @Test
    public void shouldReturnSortDescPhonesWithQuery() {
        boolean isSort = true;
        int expectedSize = 3;

        List<Phone> phones = phoneDao.sortPhonesLikeQuery(0,10,"model", "desc", BRAND);
        int size = phones.size() - 1;
        for (int i = 0; i < size; i++) {
            if (phones.get(i).getModel().compareTo(phones.get(i + 1).getModel()) < 1) {
                isSort = false;
                break;
            }
        }

        assertTrue(isSort);
        assertEquals(expectedSize, phones.size());
    }

    @Test
    public void shouldReturnSortAscPhonesWithQuery() {
        boolean isSort = true;
        int expectedSize = 3;

        List<Phone> phones = phoneDao.sortPhonesLikeQuery(0,10,"model", "asc", BRAND);
        int size = phones.size() - 1;
        for (int i = 0; i < size; i++) {
            if (phones.get(i).getModel().compareTo(phones.get(i + 1).getModel()) >= 1) {
                isSort = false;
                break;
            }
        }

        assertTrue(isSort);
        assertEquals(expectedSize, phones.size());
    }
}