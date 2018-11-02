package com.es.phoneshop.core.test;

import com.es.phoneshop.core.model.phone.Color;
import com.es.phoneshop.core.model.phone.Phone;
import com.es.phoneshop.core.model.phone.PhoneDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context/testContext.xml"})
public class JdbcPhoneDaoIntTest {

    @Autowired
    private PhoneDao phoneDao;

    private Phone testPhone;
    private Set<Color> testColorSet;
    private Color brown;
    private Color yellow;

    @Before
    public void initializeTestData() {
        testPhone = new Phone();
        testPhone.setId(8764L);
        testPhone.setBackCameraMegapixels(new BigDecimal("8.0"));
        testPhone.setBatteryCapacityMah(200);
        testPhone.setBluetooth("Yes");
        testPhone.setBrand("Apple");
        testPhone.setAnnounced(new Date());
        testPhone.setDescription("Test");
        testPhone.setDeviceType("Phone");
        testPhone.setDisplayResolution("1200x1080");
        testPhone.setDisplaySizeInches(new BigDecimal("10.5"));
        testPhone.setDisplayTechnology("Idk");
        testPhone.setFrontCameraMegapixels(new BigDecimal("15.0"));
        testPhone.setHeightMm(new BigDecimal("180.0"));
        testPhone.setImageUrl("/");
        testPhone.setInternalStorageGb(new BigDecimal("500.0"));
        testPhone.setLengthMm(new BigDecimal("100.0"));
        testPhone.setModel("Iphone 11");
        testPhone.setOs("IOS");
        testPhone.setPixelDensity(100);
        testPhone.setPositioning("Idk");
        testPhone.setPrice(new BigDecimal("1500.0"));
        testPhone.setRamGb(new BigDecimal("100.0"));
        testPhone.setStandByTimeHours(new BigDecimal("500.0"));
        testPhone.setTalkTimeHours(new BigDecimal("300.0"));
        testPhone.setWeightGr(500);
        testPhone.setWidthMm(new BigDecimal("70.0"));

        testColorSet = new HashSet<>();

        brown = new Color(1012L, "Brown");
        yellow = new Color(1002L, "Yellow");

        testColorSet.add(brown);
        testColorSet.add(yellow);
    }

    @Test
    public void checkEqualPhones() {
        phoneDao.save(testPhone);
        Assert.assertEquals(testPhone, phoneDao.get(8764L).get());
    }

    @Test
    public void checkColorsListSize() {
        phoneDao.save(testPhone);
        phoneDao.savePhoneColors(testPhone.getId(), testColorSet);
        Assert.assertEquals(phoneDao.getPhoneColors(testPhone.getId()).size(), 2);
    }

    @Test
    public void checkEqualPhoneColors() {
        phoneDao.save(testPhone);
        phoneDao.savePhoneColors(testPhone.getId(), testColorSet);
        Assert.assertEquals(phoneDao.getPhoneColors(testPhone.getId()).get(0), brown);
        Assert.assertEquals(phoneDao.getPhoneColors(testPhone.getId()).get(1), yellow);
    }

    @Test
    public void checkNotNull_Get() {
        Assert.assertNotNull(phoneDao.get(1100L));
    }

    @Test
    public void checkInstanceOf_Get() {
        Assert.assertTrue(phoneDao.get(1100L) instanceof Optional);
    }

    @Test
    public void checkInstanceOfPhone_Get() {
        Assert.assertTrue(phoneDao.get(1100L).get() instanceof Phone);
    }

    @Test
    public void checkEmptyOptional_Get() {
        Assert.assertEquals(Optional.empty(), phoneDao.get(0L));
    }

    @Test
    public void checkNonEmptyOptional_Get() {
        Assert.assertNotEquals(Optional.empty(), phoneDao.get(1100L));
    }

    @Test
    public void checkNotNull_FindAll() {
        Assert.assertNotNull(phoneDao.findAll("id+asc",10, 15));
    }

    @Test
    public void checkListLength_FindAll() {
        Assert.assertEquals(15, phoneDao.findAll("id+asc",10, 15).size());
    }

    @Test
    public void checkNotNull_GetPhoneColors() {
        Assert.assertNotNull(phoneDao.getPhoneColors(1080L));
    }
}
