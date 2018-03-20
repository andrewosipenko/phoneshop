package com.es.core;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcProductDaoIntTest{
    @Resource
    private PhoneDao phoneDao;

    private static long EXISTING_PHONE_ID = 1000L;
    private static long NOT_EXISTING_PHONE_ID = 1500L;
    private static int PHONE_AMOUNT = 8;

    @Test
    public void testAddPhone(){
        Phone phone = new Phone();
        phone.setBrand("Brrrr");
        phone.setModel("Mllll");
        phoneDao.save(phone);
        Assert.assertTrue(phone.getId() == 1009L);
    }

    @Test
    public void testGetExistingPhone(){
        Optional<Phone> optionalPhone = phoneDao.get(NOT_EXISTING_PHONE_ID);
        Assert.assertFalse(optionalPhone.isPresent());
    }

    @Test
    public void testGetNotExistingPhone(){
        Optional<Phone> optionalPhone = phoneDao.get(EXISTING_PHONE_ID);
        Assert.assertTrue(optionalPhone.isPresent());
        Assert.assertTrue(EXISTING_PHONE_ID == optionalPhone.get().getId());
    }

    @Test
    public void testFindAll(){
        List<Phone> phoneList = phoneDao.findAll(0, PHONE_AMOUNT);
        Assert.assertTrue(phoneList.size() == PHONE_AMOUNT);
    }

    @Test
    public void testSaveExistingPhone(){
        Phone phone = phoneDao.get(EXISTING_PHONE_ID).get();
        String oldModel = phone.getModel();
        String newModel = oldModel + "newModel";
        phone.setModel(newModel);
        phoneDao.save(phone);
        phone = phoneDao.get(phone.getId()).get();
        Assert.assertTrue(phone.getModel().equals(newModel));
    }

    @Test
    public void testSaveNotExistingPhone(){
        Phone phone = new Phone();
        String model = "checkModel";
        String brand = "checkBrand";
        phone.setModel(model);
        phone.setBrand(brand);
        phoneDao.save(phone);
        Assert.assertEquals(model, phoneDao.get(phone.getId()).get().getModel());
        Assert.assertEquals(brand, phoneDao.get(phone.getId()).get().getBrand());
    }
}