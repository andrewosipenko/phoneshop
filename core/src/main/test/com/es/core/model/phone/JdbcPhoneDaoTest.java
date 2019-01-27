package com.es.core.model.phone;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-test.xml")

public class JdbcPhoneDaoTest {

    private static String PHONE_BRAND = "ExpertSoft";
    private static String PHONE_MODEL = "iExpert 9";
    private static String PHONE_DESCRIPTION = "A phone for super professionals";
    private static String DEVICE_TYPE = "SUPER DUPER TYPE";
    private static long NOT_EXISTING_PHONE_ID = 12500L;
    private static int PHONE_AMOUNT = 8;
    private static long UPDATING_PHONE_ID = 1000;

    @Autowired
    private PhoneDao phoneDao;

    @Test
    public void testSavePhone(){
        Phone phone = new Phone();
        phone.setBrand(PHONE_BRAND);
        phone.setModel(PHONE_MODEL);
        phone.setDescription(PHONE_DESCRIPTION);
        phone.setDeviceType(DEVICE_TYPE);

        phoneDao.save(phone);

        Assert.assertTrue(phone.getId() > 0);

        Phone phoneFromDb = phoneDao.get(phone.getId()).get();
        Assert.assertEquals(phone, phoneFromDb);
    }

    @Test
    public void testPhoneUpdate(){
        Phone phone = phoneDao.get(UPDATING_PHONE_ID).get();

        String newModel = "newModel";
        phone.setModel(newModel);
        phoneDao.save(phone);

        phone = phoneDao.get(UPDATING_PHONE_ID).get();
        Assert.assertTrue(phone.getModel().equals(newModel));
    }

    @Test
    public void testGetNotExistingPhone(){
        Optional<Phone> optionalPhone = phoneDao.get(NOT_EXISTING_PHONE_ID);
        System.out.println(optionalPhone.toString());
        Assert.assertFalse(optionalPhone.isPresent());
    }

    @Test
    public void testFindAll(){
        List<Phone> phoneList = phoneDao.findAll(0, PHONE_AMOUNT);
        Assert.assertEquals(phoneList.size(), PHONE_AMOUNT);
    }

}
