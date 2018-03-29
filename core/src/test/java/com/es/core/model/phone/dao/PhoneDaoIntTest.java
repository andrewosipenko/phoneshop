package com.es.core.model.phone.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.util.PhoneDaoSelector;
import com.es.core.model.phone.dao.util.SortBy;
import com.es.core.testutils.json.JSONAssertionData;
import com.es.core.testutils.PhoneComparator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
@Transactional
public class PhoneDaoIntTest {
    @Autowired
    private PhoneDao phoneDao;
    private static Comparator<Phone> phoneComparator;
    private static JSONAssertionData assertionData;

    private static final long EXISTING_RANGE_BEGIN = 1000L;
    private static final long EXISTING_RANGE_END = 1020L;
    private static final long UNEXISTING = 999L;
    private static final long EXISTING_WITH_COLORS_PRESENT = 1001L;
    private static final long EXISTING_WITH_BLACK_COLOR = 1011L;
    private static final long EXISTING_3 = 1000L;
    private static final long[] FIRST_8_IN_STOCK_PRICE_NE = {1001L, 1002L, 1003L, 1004L, 1005L, 1011L, 1012L, 1013L};
    private static final long[] SEARCH_TITANIUM_IN_STOCK_PRICE_NE = {1005L, 1012L, 1013L, 1015L};
    private static final long[] SEARCH_101_SORT_BY_PRICE_IN_STOCK_PRICE_NE = {1003L, 1004L, 1005L, 1002L, 1001L};
    private static final int TOTAL_IN_STOCK_PRICE_NE = 9;
    private static final int SEARCHING_INTERNET_IN_STOCK_PRICE_NE = 1;

    @BeforeClass
    public static void initAssertionData() throws IOException, ParseException {
        assertionData = new JSONAssertionData("assertion-data/phones.json");
        phoneComparator = new PhoneComparator();
    }

    @Test
    public void testGetExisting() {
        for (long id = EXISTING_RANGE_BEGIN; id <= EXISTING_RANGE_END; id++) {
            Optional<Phone> phoneOptional = phoneDao.get(id);
            assertTrue(phoneOptional.isPresent());
            Phone phone = phoneOptional.get();
            Phone expectedPhone = assertionData.getPhone(id);
            assertTrue(phoneComparator.compare(phone, expectedPhone) == 0);
        }
    }

    @Test
    public void testGetUnexisting() {
        Optional<Phone> phoneOptional = phoneDao.get(UNEXISTING);
        assertFalse(phoneOptional.isPresent());
    }

    @Test
    public void textSaveWhenUpdatingExisting() {
        Optional<Phone> phoneOptional = phoneDao.get(EXISTING_WITH_BLACK_COLOR);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setBrand("Apple");
        phone.getColors().removeIf(color -> color.getId().equals(1000L));
        Color colorToAdd = new Color();
        colorToAdd.setId(1004L);
        colorToAdd.setCode("Red");
        phone.getColors().add(colorToAdd);
        phoneDao.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneDao.get(EXISTING_WITH_BLACK_COLOR);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertTrue(phoneComparator.compare(phone, updatedPhone) == 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSaveWhenUpdatingExistingWithColorListRemoval() {
        Optional<Phone> phoneOptional = phoneDao.get(EXISTING_WITH_COLORS_PRESENT);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setColors(Collections.EMPTY_SET);
        phoneDao.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneDao.get(EXISTING_WITH_COLORS_PRESENT);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertTrue(phoneComparator.compare(phone, updatedPhone) == 0);
    }

    @Test
    public void testSaveWhenInsertingCorrect() {
        Optional<Phone> phoneOptional = phoneDao.get(EXISTING_3);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(null);
        phone.setModel("Plastmassovyi mir pobedil");
        phoneDao.save(phone);
        long id = phone.getId();
        Optional<Phone> insertedPhoneOptional = phoneDao.get(id);
        assertTrue(insertedPhoneOptional.isPresent());
        Phone insertedPhone = phoneOptional.get();
        assertTrue(phoneComparator.compare(phone, insertedPhone) == 0);
    }

    @Test
    public void testSaveWhenUpdatingUnexisting() {
        Optional<Phone> phoneOptional = phoneDao.get(EXISTING_3);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(UNEXISTING);
        phone.setModel("Maket okazalsia silnei");
        try {
            phoneDao.save(phone);
            fail();
        } catch (DataIntegrityViolationException ignored) {}
    }

    @Test
    public void testSaveWhenSavingDuplicate() {
        Optional<Phone> phoneOptional = phoneDao.get(EXISTING_3);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(null);
        try {
            phoneDao.save(phone);
            fail();
        } catch (DataIntegrityViolationException ignored) {}
    }

    @Test
    public void testFindAllStockOnly() {
        List<Phone> phoneList = phoneDao.findAll(0, 8);
        assertTrue(phoneList.size() == 8);
        for (int i = 0; i < 8; i++)
            assertTrue(phoneComparator.compare(phoneList.get(i), assertionData.getPhone(FIRST_8_IN_STOCK_PRICE_NE[i])) == 0);
    }

    @Test
    public void testFindAllWithSelectorSearching() {
        List<Phone> phoneList = phoneDao.findAll(new PhoneDaoSelector().searching("titanium"));
        phoneList.sort(Comparator.comparing(Phone::getId));
        assertEquals(phoneList.size(), SEARCH_TITANIUM_IN_STOCK_PRICE_NE.length);
        for (int i = 0; i < phoneList.size(); i++)
            assertTrue(phoneComparator.compare(phoneList.get(i), assertionData.getPhone(SEARCH_TITANIUM_IN_STOCK_PRICE_NE[i])) == 0);
    }

    @Test
    public void testFindAllWithSelectorSearchingAndSorting() {
        List<Phone> phoneList = phoneDao.findAll(new PhoneDaoSelector().searching("101").sortedBy(SortBy.PRICE));
        assertEquals(phoneList.size(), SEARCH_101_SORT_BY_PRICE_IN_STOCK_PRICE_NE.length);
        for (int i = 0; i < phoneList.size(); i++)
            assertTrue(phoneComparator.compare(phoneList.get(i), assertionData.getPhone(SEARCH_101_SORT_BY_PRICE_IN_STOCK_PRICE_NE[i])) == 0);
    }

    @Test
    public void testCountStockOnly() {
        int num = phoneDao.count();
        assertEquals(num, TOTAL_IN_STOCK_PRICE_NE);
    }

    @Test
    public void testCountWithSelector() {
        int num = phoneDao.count(new PhoneDaoSelector().searching("Internet"));
        assertEquals(num, SEARCHING_INTERNET_IN_STOCK_PRICE_NE);
    }
}