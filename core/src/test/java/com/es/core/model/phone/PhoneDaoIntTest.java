package com.es.core.model.phone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context/applicationContext-core.xml")
@Sql({"/db/schema.sql", "classpath:db/demodata-phones.sql"})
public class PhoneDaoIntTest {

    @Autowired
    private PhoneDao phoneDao;

    @Test
    public void getTest() {
        Optional<Phone> optionalPhone = phoneDao.get(1011L);
        Assert.assertNotNull(optionalPhone.get());
        Phone phone = optionalPhone.get();
        Assert.assertEquals("ARCHOS", phone.getBrand());
        Assert.assertEquals(3, phone.getColors().size());
    }

    @Test
    public void saveTest() {
        Optional<Phone> optionalPhone = phoneDao.get(1000L);
        Assert.assertNotNull(optionalPhone.get());
        Phone phone = optionalPhone.get();
        phone.setId(8764L);
        phone.setModel("new Model");
        phone.setBrand("new Brand");
        phoneDao.save(phone);
        Optional<Phone> newOptionalPhone = phoneDao.get(8764L);
        Assert.assertNotNull(newOptionalPhone);
        Phone newPhone = newOptionalPhone.get();
        Assert.assertEquals(phone.getDescription(), newPhone.getDescription());
        Assert.assertEquals(phone.getDeviceType(), newPhone.getDeviceType());
        Assert.assertEquals(phone.getBluetooth(), newPhone.getBluetooth());
        Assert.assertEquals(phone.getColors().size(), newPhone.getColors().size());
    }
}
