package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext-core.xml",
        "classpath:applicationContext-demoda-test.xml"})
public class JdbcProductDaoIntTest {

    private static final int ROUNDING_COUNT = 10;

    @Autowired
    private ProductDao productDao;

    @Test
    public void testLoadPhoneWithID_1101() throws ParseException {
        Optional<Phone> optionalPhone = productDao.load(1101L);
        if (!optionalPhone.isPresent()) {
            Assert.fail();
        }
        Phone actual = optionalPhone.get();
        Phone expected = getExpectedPhoneWithID_1101();
        assertEqualsPhones(actual, expected);
    }

    private Phone getExpectedPhoneWithID_1101() throws ParseException {
        Phone phone = new Phone();
        phone.setId(1101L);
        phone.setBrand("Acer");
        phone.setModel("Acer Liquid E3");
        phone.setDisplaySizeInches(new BigDecimal(4.7));
        phone.setWeightGr(156);
        phone.setLengthMm(new BigDecimal(138.0));
        phone.setWidthMm(new BigDecimal(69.0));
        phone.setHeightMm(new BigDecimal(8.9));
        phone.setAnnounced(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-02-20 00:00:00"));
        phone.setDeviceType("Smart phone");
        phone.setOs("Android (4.2.2)");
        phone.setColors(Stream.of(new Color(1000L, "Black")).collect(Collectors.toSet()));
        phone.setDisplayResolution("720 x  1280");
        phone.setPixelDensity(312);
        phone.setDisplayTechnology("IPS LCD");
        phone.setBackCameraMegapixels(new BigDecimal(13.0));
        phone.setFrontCameraMegapixels(new BigDecimal(2.0));
        phone.setRamGb(new BigDecimal(1.0));
        phone.setInternalStorageGb(new BigDecimal(4.0));
        phone.setBatteryCapacityMah(0);
        phone.setTalkTimeHours(new BigDecimal(5.0));
        phone.setStandByTimeHours(new BigDecimal(260.0));
        phone.setBluetooth("Yes");
        phone.setPositioning("GPS, A-GPS");
        phone.setImageUrl("manufacturer/Acer/Acer Liquid E3.jpg");
        phone.setDescription("The Acer Liquid E3 will cost €199 ($275 USD) and features a 4.7 inch screen with a 720 x 1280 resolution. A quad-core 1.2GHz processor is under the hood with 1GB of RAM on board and 4GB of native storage. The rear and front-facing snappers weigh in at 13MP and 2MP respectively, making this the model for the struggling (or penny wise) photographer.");
        return phone;
    }

    private void assertEqualsPhones(Phone phone1, Phone phone2) {
        Assert.assertEquals(phone1.getId(), phone2.getId());
        Assert.assertEquals(phone1.getBrand(), phone2.getBrand());
        Assert.assertEquals(phone1.getModel(), phone2.getModel());
        Assert.assertEquals(phone1.getPrice(), phone2.getPrice());
        Assert.assertEquals(phone1.getDisplaySizeInches().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getDisplaySizeInches().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getWeightGr(), phone2.getWeightGr());
        Assert.assertEquals(phone1.getLengthMm().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getLengthMm().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getWidthMm().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getWidthMm().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getHeightMm().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getHeightMm().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getAnnounced(), phone2.getAnnounced());
        Assert.assertEquals(phone1.getDeviceType(), phone2.getDeviceType());
        Assert.assertEquals(phone1.getOs(), phone2.getOs());
        Assert.assertEquals(phone1.getColors(), phone2.getColors());

        Assert.assertEquals(phone1.getDisplayResolution(), phone2.getDisplayResolution());
        Assert.assertEquals(phone1.getPixelDensity(), phone2.getPixelDensity());
        Assert.assertEquals(phone1.getDisplayTechnology(), phone2.getDisplayTechnology());
        Assert.assertEquals(phone1.getBackCameraMegapixels().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getBackCameraMegapixels().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getFrontCameraMegapixels().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getFrontCameraMegapixels().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getRamGb().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getRamGb().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getInternalStorageGb().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getInternalStorageGb().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getBatteryCapacityMah(), phone2.getBatteryCapacityMah());
        Assert.assertEquals(phone1.getTalkTimeHours().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getTalkTimeHours().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getStandByTimeHours().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN), phone2.getStandByTimeHours().setScale(ROUNDING_COUNT, RoundingMode.HALF_DOWN));
        Assert.assertEquals(phone1.getBluetooth(), phone2.getBluetooth());
        Assert.assertEquals(phone1.getPositioning(), phone2.getPositioning());
        Assert.assertEquals(phone1.getImageUrl(), phone2.getImageUrl());
        Assert.assertEquals(phone1.getDescription(), phone2.getDescription());
    }
}
