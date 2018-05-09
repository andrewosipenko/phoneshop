package com.es.phoneshop.core.phone.service;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Color;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.testutils.PhoneComparator;
import com.es.phoneshop.testutils.json.JSONAssertionData;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class PhoneServiceIntTest {
    @Resource
    private PhoneService phoneService;

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
            Optional<Phone> phoneOptional = phoneService.getPhone(id);
            assertTrue(phoneOptional.isPresent());
            Phone phone = phoneOptional.get();
            Phone expectedPhone = assertionData.getPhone(id);
            assertEquals(0, phoneComparator.compare(phone, expectedPhone));
        }
    }

    @Test
    public void testGetUnexisting() {
        Optional<Phone> phoneOptional = phoneService.getPhone(UNEXISTING);
        assertFalse(phoneOptional.isPresent());
    }

    @Test
    public void textSaveWhenUpdatingExisting() {
        Optional<Phone> phoneOptional = phoneService.getPhone(EXISTING_WITH_BLACK_COLOR);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setBrand("Apple");
        phone.getColors().removeIf(color -> color.getId().equals(1000L));
        Color colorToAdd = new Color();
        colorToAdd.setId(1004L);
        colorToAdd.setCode("Red");
        phone.getColors().add(colorToAdd);
        phoneService.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneService.getPhone(EXISTING_WITH_BLACK_COLOR);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertEquals(0, phoneComparator.compare(phone, updatedPhone));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSaveWhenUpdatingExistingWithColorListRemoval() {
        Optional<Phone> phoneOptional = phoneService.getPhone(EXISTING_WITH_COLORS_PRESENT);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setColors(Collections.EMPTY_SET);
        phoneService.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneService.getPhone(EXISTING_WITH_COLORS_PRESENT);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertEquals(0, phoneComparator.compare(phone, updatedPhone));
    }

    @Test
    public void testSaveWhenInserting() {
        Optional<Phone> phoneOptional = phoneService.getPhone(EXISTING_3);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(null);
        phone.setModel("Plastmassovyi mir pobedil");
        phoneService.save(phone);
        long id = phone.getId();
        Optional<Phone> insertedPhoneOptional = phoneService.getPhone(id);
        assertTrue(insertedPhoneOptional.isPresent());
        Phone insertedPhone = phoneOptional.get();
        assertEquals(0, phoneComparator.compare(phone, insertedPhone));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveWhenUpdatingUnexisting() {
        Optional<Phone> phoneOptional = phoneService.getPhone(EXISTING_3);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(UNEXISTING);
        phone.setModel("Maket okazalsia silnei");
        phoneService.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveWhenSavingDuplicate() {
        Optional<Phone> phoneOptional = phoneService.getPhone(EXISTING_3);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(null);
        phoneService.save(phone);
    }

    @Test
    public void testFindAll() {
        List<Phone> phoneList = phoneService.getPhoneList(null, null, 0, 8);
        assertEquals(8, phoneList.size());
        for (int i = 0; i < 8; i++)
            assertEquals(0, phoneComparator.compare(phoneList.get(i), assertionData.getPhone(FIRST_8_IN_STOCK_PRICE_NE[i])));
    }

    @Test
    public void testFindAllWithSelectorSearching() {
        List<Phone> phoneList = phoneService.getPhoneList("titanium", null, null, null);
        phoneList.sort(Comparator.comparing(Phone::getId));
        assertEquals(phoneList.size(), SEARCH_TITANIUM_IN_STOCK_PRICE_NE.length);
        for (int i = 0; i < phoneList.size(); i++)
            assertEquals(0, phoneComparator.compare(phoneList.get(i), assertionData.getPhone(SEARCH_TITANIUM_IN_STOCK_PRICE_NE[i])));
    }

    @Test
    public void testFindAllWithSelectorSearchingAndSorting() {
        List<Phone> phoneList = phoneService.getPhoneList("101", SortBy.PRICE, 0, null);
        assertEquals(phoneList.size(), SEARCH_101_SORT_BY_PRICE_IN_STOCK_PRICE_NE.length);
        for (int i = 0; i < phoneList.size(); i++)
            assertEquals(0, phoneComparator.compare(phoneList.get(i), assertionData.getPhone(SEARCH_101_SORT_BY_PRICE_IN_STOCK_PRICE_NE[i])));
    }

    @Test
    public void testCount() {
        int num = phoneService.countPhones(null);
        assertEquals(num, TOTAL_IN_STOCK_PRICE_NE);
    }

    @Test
    public void testCountWithSelector() {
        int num = phoneService.countPhones("Internet");
        assertEquals(num, SEARCHING_INTERNET_IN_STOCK_PRICE_NE);
    }
}
