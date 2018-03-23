package com.es.core.model.phone.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
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

    private static final Long MIN_TEST_DATA_ID = 1000L;
    private static final Long MAX_TEST_DATA_ID = 1020L;
    private static final Long UNEXISTING_PHONE_ID = 999L;


    @BeforeClass
    public static void initAssertionData() throws IOException, ParseException {
        assertionData = new JSONAssertionData("assertion-data/phones.json");
        phoneComparator = new PhoneComparator();
    }

    @Test
    public void testGetExisting() {
        for (long id = MIN_TEST_DATA_ID; id <= MAX_TEST_DATA_ID; id++) {
            Optional<Phone> phoneOptional = phoneDao.get(id);
            assertTrue(phoneOptional.isPresent());
            Phone phone = phoneOptional.get();
            Phone expectedPhone = assertionData.getPhone(id);
            assertTrue(phoneComparator.compare(phone, expectedPhone) == 0);
        }
    }

    @Test
    public void testGetUnexisting() {
        Optional<Phone> phoneOptional = phoneDao.get(999L);
        assertFalse(phoneOptional.isPresent());
    }

    @Test
    public void textSaveWhenUpdatingExisting() {
        long id = 1011L;
        Optional<Phone> phoneOptional = phoneDao.get(id);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setBrand("Apple");
        Color colorToRemove = new Color(), colorToAdd = new Color();
        colorToRemove.setId(1000L);
        colorToAdd.setId(1004L);
        colorToRemove.setCode("Black");
        colorToAdd.setCode("Red");
        phone.getColors().remove(colorToRemove);
        phone.getColors().add(colorToAdd);
        phoneDao.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneDao.get(id);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertTrue(phoneComparator.compare(phone, updatedPhone) == 0);
    }

    @Test
    public void testSaveWhenUpdatingExistingWithColorListRemoval() {
        long id = 1001L;
        Optional<Phone> phoneOptional = phoneDao.get(id);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setColors(Collections.EMPTY_SET);
        phoneDao.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneDao.get(id);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertTrue(phoneComparator.compare(phone, updatedPhone) == 0);
    }

    @Test
    public void testSaveWhenInsertingCorrect() {
        long id = 1000L;
        Optional<Phone> phoneOptional = phoneDao.get(id);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(null);
        phone.setModel("Plastmassovyi mir pobedil");
        phoneDao.save(phone);
        id = phone.getId();
        Optional<Phone> insertedPhoneOptional = phoneDao.get(id);
        assertTrue(insertedPhoneOptional.isPresent());
        Phone insertedPhone = phoneOptional.get();
        assertTrue(phoneComparator.compare(phone, insertedPhone) == 0);
    }

    @Test
    public void testSaveWhenUpdatingUnexisting() {
        long id = 1000L;
        Optional<Phone> phoneOptional = phoneDao.get(id);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(UNEXISTING_PHONE_ID);
        phone.setModel("Maket okazalsia silnei");
        try {
            phoneDao.save(phone);
            fail();
        } catch (DataIntegrityViolationException ignored) {}
    }

    @Test
    public void testSaveWhenSavingDuplicate() {
        long id = 1000L;
        Optional<Phone> phoneOptional = phoneDao.get(id);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setId(null);
        try {
            phoneDao.save(phone);
            fail();
        } catch (DataIntegrityViolationException ignored) {}
    }

    @Test
    public void testFindAllOnCorrectInterval() {
        List<Phone> phoneList = phoneDao.findAll(9, 5);
        assertTrue(phoneList.size() == 5);
        for (Phone phone : phoneList)
            assertTrue(phoneComparator.compare(phone, assertionData.getPhone(phone.getId())) == 0);
    }

    @Test
    public void testFindAllOnOutOfBoundsInterval() {
        List<Phone> phoneList = phoneDao.findAll(9, (int) (MAX_TEST_DATA_ID - MIN_TEST_DATA_ID + 5));
        assertTrue(phoneList.size() == assertionData.getSize() - 9);
        for (Phone phone : phoneList)
            assertTrue(phoneComparator.compare(phone, assertionData.getPhone(phone.getId())) == 0);
    }
}