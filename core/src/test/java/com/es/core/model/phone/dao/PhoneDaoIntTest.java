package com.es.core.model.phone.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.testutils.JSONAssertionData;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationIntTestContext.xml")
public class PhoneDaoIntTest {
    @Autowired
    private PhoneDao phoneDao;
    private static JSONAssertionData assertionData;

    @BeforeClass
    public static void initAssertionData() throws IOException, ParseException {
        assertionData = new JSONAssertionData("assertion-data/phones.json");
    }

    @Test
    public void getExistingPhone() {
        assertNotNull(phoneDao);
        for (long id = 1000L; id <= 1020L; id++) {
            Optional<Phone> phoneOptional = phoneDao.get(id);
            assertTrue(phoneOptional.isPresent());
            Phone phone = phoneOptional.get();
            Phone expectedPhone = assertionData.getPhone(id);
            assertEquals(phone, expectedPhone);
        }
    }

    @Test
    public void getUnexistingPhone() {
        assertNotNull(phoneDao);
        Optional<Phone> phoneOptional = phoneDao.get(999L);
        assertFalse(phoneOptional.isPresent());
    }

    @Test
    public void getAndUpdatePhone() {
        long id = 1011L;
        assertNotNull(phoneDao);
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
        assertEquals(phone, updatedPhone);
        assertionData.reflectChanges(phone);
    }

    @Test
    public void getPhoneAndUpdateByRemovingColors() {
        long id = 1001L;
        assertNotNull(phoneDao);
        Optional<Phone> phoneOptional = phoneDao.get(id);
        assertTrue(phoneOptional.isPresent());
        Phone phone = phoneOptional.get();
        phone.setColors(Collections.EMPTY_SET);
        phoneDao.save(phone);
        Optional<Phone> updatedPhoneOptional = phoneDao.get(id);
        assertTrue(updatedPhoneOptional.isPresent());
        Phone updatedPhone = phoneOptional.get();
        assertEquals(phone, updatedPhone);
        assertionData.reflectChanges(phone);
    }

    @Test
    public void insertPhone() {
        long id = 1000L;
        assertNotNull(phoneDao);
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
        assertEquals(phone, insertedPhone);
        assertionData.reflectChanges(phone);
    }

    @Test
    public void findPhones() {
        assertNotNull(phoneDao);
        List<Phone> phoneList = phoneDao.findAll(9, 5);
        assertTrue(phoneList.size() == 5);
        for (Phone phone : phoneList)
            assertEquals(phone, assertionData.getPhone(phone.getId()));
    }

    @Test
    public void findPhonesOutOfBounds() {
        assertNotNull(phoneDao);
        List<Phone> phoneList = phoneDao.findAll(9, 200);
        assertTrue(phoneList.size() == assertionData.getSize() - 9);
        for (Phone phone : phoneList)
            assertEquals(phone, assertionData.getPhone(phone.getId()));
    }
}