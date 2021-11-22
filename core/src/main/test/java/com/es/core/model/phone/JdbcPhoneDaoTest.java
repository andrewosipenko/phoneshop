package com.es.core.model.phone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcPhoneDaoTest {
    public static final long NEW_PHONE_ID = 1009L;
    public static final String COLOR_CODE_BLACK = "Black";
    @Resource
    private PhoneDao phoneDao;
    private final static String PHONE_BRAND = "Test";
    private final static String PHONE_MODEL = "Test";
    private final static String PHONE_DESCRIPTION = "Test";
    private final static String DEVICE_TYPE = "Test";
    private final long EXISTING_PHONE_ID_0 = 1000L;
    private final long EXISTING_PHONE_ID_2 = 1002L;
    private final long EXISTING_PHONE_ID_3 = 1003L;

    @Test
    public void shouldPhoneDaoExistMethod() {
        Assert.assertNotNull(phoneDao);
    }

    @Test
    public void shouldSavePhoneMethod() {
        Phone actualPhone = new Phone();
        actualPhone.setBrand(PHONE_BRAND);
        actualPhone.setModel(PHONE_MODEL);
        actualPhone.setDescription(PHONE_DESCRIPTION);
        actualPhone.setDeviceType(DEVICE_TYPE);
        actualPhone.setWeightGr(1);
        actualPhone.setPixelDensity(1);
        actualPhone.setBatteryCapacityMah(1);
        phoneDao.save(actualPhone);

        Assert.assertTrue(actualPhone.getId() > 0);

        Phone expectedPhone = phoneDao.get(actualPhone.getId()).get();
        Assert.assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldGetColorWhenGetFromPhoneTableMethod() {
        if (phoneDao.get(1000L).isPresent()) {
            Phone actualPhone = phoneDao.get(EXISTING_PHONE_ID_0).get();
            Set<Color> colorSet = new HashSet<>();
            Color color = new Color();
            color.setCode("Black");
            color.setId(1000L);
            colorSet.add(color);
            Assert.assertEquals(colorSet, actualPhone.getColors());
        } else {
            Assert.fail();
        }
    }

    @Test
    public void shouldFindAllMethod() {
        List<Phone> phoneListExpected = phoneDao.findAll(2, 2);
        List<Phone> phoneListActual = new ArrayList<>();
        if (phoneDao.get(1000L).isPresent() && phoneDao.get(1001L).isPresent()) {
            phoneListActual.add(phoneDao.get(EXISTING_PHONE_ID_2).get());
            phoneListActual.add(phoneDao.get(EXISTING_PHONE_ID_3).get());
            Assert.assertEquals(phoneListExpected, phoneListActual);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetColorSetWhenGetPhoneWithColorSetMethod() {
        if (phoneDao.get(1000L).isPresent()) {
            Phone actualPhone = phoneDao.get(EXISTING_PHONE_ID_0).get();
            Assert.assertNotNull(actualPhone.getColors());
        } else {
            Assert.fail();
        }
    }

    @Test
    public void shouldSaveColorWhenSavePhoneMethod() {
        if (phoneDao.get(1000L).isPresent()) {
            Phone actualPhone = new Phone();
            actualPhone.setBrand(PHONE_BRAND);
            actualPhone.setModel(PHONE_MODEL);
            actualPhone.setDescription(PHONE_DESCRIPTION);
            actualPhone.setDeviceType(DEVICE_TYPE);
            actualPhone.setWeightGr(1);
            actualPhone.setPixelDensity(1);
            actualPhone.setBatteryCapacityMah(1);
            Set<Color> colorSet = new HashSet<>();
            Color color = new Color();
            color.setCode(COLOR_CODE_BLACK);
            color.setId(EXISTING_PHONE_ID_0);
            actualPhone.setColors(colorSet);
            colorSet.add(color);
            phoneDao.save(actualPhone);
            if (phoneDao.get(NEW_PHONE_ID).isPresent()) {
                Phone expectedPhone = phoneDao.get(1009L).get();
                Assert.assertEquals(actualPhone, expectedPhone);
            } else {
                Assert.fail();
            }
        } else {
            Assert.fail();
        }
    }

}
