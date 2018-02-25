package com.es.core.model.phone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/integrationTestContext.xml")
@TestPropertySource(properties = {"db.testSchemaSource=db/jdbcPhoneDaoIntTest-schemaSource.sql"})
public class JdbcPhoneDaoIntTest {

    @Resource
    private JdbcPhoneDao jdbcPhoneDao;

    private static Phone mockPhone;

    @BeforeClass
    public static void setUpBeforeClass() {
        mockPhone = new Phone();

        mockPhone.setId(777L);
        mockPhone.setBrand("mockBrand");
        mockPhone.setModel("mockModel");
        mockPhone.setPrice(BigDecimal.valueOf(123.0));
        mockPhone.setDisplaySizeInches(BigDecimal.valueOf(1.1));
        mockPhone.setWeightGr(100);
        mockPhone.setLengthMm(BigDecimal.valueOf(13.0));
        mockPhone.setWidthMm(BigDecimal.valueOf(13.0));
        mockPhone.setHeightMm(BigDecimal.valueOf(14.0));
        mockPhone.setAnnounced(Date.from(Instant.now()));
        mockPhone.setDeviceType("mockType");
        mockPhone.setOs("mockOs");
        mockPhone.setDisplayResolution("mockResolution");
        mockPhone.setPixelDensity(68);
        mockPhone.setDisplayTechnology("mockTechnology");
        mockPhone.setBackCameraMegapixels(BigDecimal.valueOf(15.0));
        mockPhone.setFrontCameraMegapixels(BigDecimal.valueOf(16.0));
        mockPhone.setRamGb(BigDecimal.valueOf(17.0));
        mockPhone.setInternalStorageGb(BigDecimal.valueOf(18.0));
        mockPhone.setBatteryCapacityMah(2000);
        mockPhone.setTalkTimeHours(BigDecimal.valueOf(19.0));
        mockPhone.setStandByTimeHours(BigDecimal.valueOf(20.0));
        mockPhone.setBluetooth("mockBluetooth");
        mockPhone.setPositioning("mockPositioning");
        mockPhone.setImageUrl("mockImageUrl");
        mockPhone.setDescription("mockDescription");
    }

    @Test
    public void get_IdEquals999_ReturnEmptyOptional() {
        assertFalse(jdbcPhoneDao.get(999L).isPresent());
    }

    @Test
    public void get_IdEquals1001_ReturnNotEmptyOptional(){
        assertTrue(jdbcPhoneDao.get(1001L).isPresent());
    }

    @Test
    public void get_IdEquals1001_ReturnCorrectData() {
        Optional<Phone> phone = jdbcPhoneDao.get(1001L);
        Long actual = phone.get().getId();
        Long expected = 1001L;

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void save_InsertPhone() {
        Phone expected = mockPhone;

        jdbcPhoneDao.save(expected);
        Phone actual = jdbcPhoneDao.get(mockPhone.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void save_UpdatePhone() {
        jdbcPhoneDao.save(mockPhone);

        Phone expected = mockPhone;
        expected.setModel("UpdatedMockModel");
        expected.setBrand("UpdatedMockBrand");
        jdbcPhoneDao.save(expected);

        Phone actual = jdbcPhoneDao.get(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void findAll_3Rows_Offset0_Limit2_ReturnListSize2() {
        List<Phone> phones = jdbcPhoneDao.findAll(0, 2);
        int expected = 2;
        int actual = phones.size();

        assertEquals(expected, actual);
    }


    @Test
    public void findAll_3Rows_Offset2_Limit2_ReturnListSize1() {
        List<Phone> phones = jdbcPhoneDao.findAll(2, 2);
        int expected = 1;
        int actual = phones.size();

        assertEquals(expected, actual);
    }
}

