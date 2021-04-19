package com.es.core.model.phone;

import com.es.core.exception.ArgumentIsNullException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:resources/context/applicationContext-core-test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcPhoneDaoIntTest {
    @Resource
    private JdbcPhoneDao phoneDao;
    @Resource
    private JdbcTemplate jdbcTemplate;
    private static final String COUNT_ROWS_IN_PHONE = "select count(*) from phones";
    private static final String LAST_PHONE_ID = "select max(id) from phones";
    private static final String COLOR_BY_PHONE = "select colorId from phone2color where phoneId = ?";

    @Test
    public void testGetPhoneNotExist() {
        Long id = 1010L;
        Optional<Phone> actualPhone = phoneDao.get(id);
        assertFalse(actualPhone.isPresent());
    }

    @Test
    public void testGetPhoneWithOneColor() {
        Long id = 1000L;
        Optional<Phone> actualPhone = phoneDao.get(id);
        assertTrue(actualPhone.isPresent());
        assertEquals(id, actualPhone.get().getId());
        assertEquals(1, actualPhone.get().getColors().size());
        assertEquals(1000L, actualPhone.get().getColors().iterator().next().getId().longValue());
    }

    @Test
    public void testGetPhoneWithManyColors() {
        Long id = 1001L;
        Optional<Phone> actualPhone = phoneDao.get(id);
        assertTrue(actualPhone.isPresent());
        assertEquals(id, actualPhone.get().getId());
        assertEquals(2, actualPhone.get().getColors().size());
        List<Long> colorsId = actualPhone.get().getColors().stream().map(Color::getId).collect(Collectors.toList());
        assertTrue(colorsId.contains(1000L));
        assertTrue(colorsId.contains(1001L));
    }

    @Test
    public void testGetPhoneWithoutColor() {
        Long id = 1003L;
        Optional<Phone> actualPhone = phoneDao.get(id);
        assertTrue(actualPhone.isPresent());
        assertEquals(id, actualPhone.get().getId());
        assertTrue(actualPhone.get().getColors().isEmpty());
    }

    @Test
    public void testGetPhoneNull() {
        Long id = null;
        Optional<Phone> actualPhone = phoneDao.get(id);
        assertFalse(actualPhone.isPresent());
    }

    @Test
    public void testSavePhoneNotExistWithoutColors() {
        Phone phone = createPhone();
        long countBeforeSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        phoneDao.save(phone);
        long countAfterSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        assertEquals(1, countAfterSaving - countBeforeSaving);
        Long phoneId = jdbcTemplate.queryForObject(LAST_PHONE_ID, Long.class);
        assertNotNull(phoneId);
        List<Long> colorIds = jdbcTemplate.query(COLOR_BY_PHONE, new SingleColumnRowMapper<>(), phoneId);
        assertEquals(0, colorIds.size());
    }

    @Test
    public void testSavePhoneNotExistWithColors() {
        Long colorIdFirst = 1001L;
        Long colorIdSecond = 1002L;
        Phone phone = createPhone();
        setColorsToPhone(phone, colorIdFirst, colorIdSecond);
        long countBeforeSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        phoneDao.save(phone);
        long countAfterSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        assertEquals(1, countAfterSaving - countBeforeSaving);
        Long phoneId = jdbcTemplate.queryForObject(LAST_PHONE_ID, Long.class);
        assertNotNull(phoneId);
        List<Long> colorIds = jdbcTemplate.query(COLOR_BY_PHONE, new SingleColumnRowMapper<>(), phoneId);
        assertEquals(2, colorIds.size());
        assertTrue(colorIds.contains(colorIdFirst));
        assertTrue(colorIds.contains(colorIdSecond));
    }

    @Test
    public void testSavePhoneExistWithoutColors() {
        Phone phone = createPhone();
        Long phoneId = 1002L;
        phone.setId(phoneId);
        long countBeforeSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        phoneDao.save(phone);
        long countAfterSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        assertEquals(0, countAfterSaving - countBeforeSaving);
        List<Long> colorIds = jdbcTemplate.query(COLOR_BY_PHONE, new SingleColumnRowMapper<>(), phoneId);
        assertEquals(0, colorIds.size());
    }

    @Test
    public void testSavePhoneExistWithColors() {
        Long colorIdFirst = 1001L;
        Long colorIdSecond = 1002L;
        Phone phone = createPhone();
        Long phoneId = 1002L;
        phone.setId(phoneId);
        setColorsToPhone(phone, colorIdFirst, colorIdSecond);
        long countBeforeSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        phoneDao.save(phone);
        long countAfterSaving = jdbcTemplate.queryForObject(COUNT_ROWS_IN_PHONE, Long.class);
        assertEquals(0, countAfterSaving - countBeforeSaving);
        List<Long> colorIds = jdbcTemplate.query(COLOR_BY_PHONE, new SingleColumnRowMapper<>(), phoneId);
        assertEquals(2, colorIds.size());
        assertTrue(colorIds.contains(colorIdFirst));
        assertTrue(colorIds.contains(colorIdSecond));
    }

    @Test(expected = ArgumentIsNullException.class)
    public void testSavePhoneNull() {
        Phone phone = null;
        phoneDao.save(phone);
        fail("Phone can't be null");
    }

    @Test(expected = ArgumentIsNullException.class)
    public void testSavePhoneWithNullBrandOrModel() {
        Phone phone = new Phone();
        phoneDao.save(phone);
        fail("Brand or model can't be null");
    }

    @Test
    public void testFindAll() {
        List<Phone> phones = phoneDao.findAll(0, 4);
        assertEquals(4, phones.size());
        assertEquals(1000L, phones.get(0).getId().longValue());
        assertEquals(1003L, phones.get(3).getId().longValue());
    }

    @Test
    public void testFindAllWithOffset() {
        List<Phone> phones = phoneDao.findAll(2, 2);
        assertEquals(2, phones.size());
        assertEquals(1002L, phones.get(0).getId().longValue());
        assertEquals(1003L, phones.get(1).getId().longValue());
    }

    @Test
    public void testFindAllWithBigLimit() {
        List<Phone> phones = phoneDao.findAll(2, 10);
        assertEquals(3, phones.size());
        assertEquals(1002L, phones.get(0).getId().longValue());
        assertEquals(1004L, phones.get(2).getId().longValue());
    }

    @Test
    public void testFindAllWithBigOffset() {
        List<Phone> phones = phoneDao.findAll(6, 10);
        assertTrue(phones.isEmpty());
    }

    private Phone createPhone() {
        Phone phone = new Phone();
        String brand = "ARCHOS";
        String model = "ARCHOS 102";
        phone.setBrand(brand);
        phone.setModel(model);
        return phone;
    }

    private void setColorsToPhone(Phone phone, Long colorIdFirst, Long colorIdSecond) {
        Set<Color> colors = new HashSet<>(Arrays.asList(
                new Color(colorIdFirst, "White"),
                new Color(colorIdSecond, "Yellow")));
        phone.setColors(colors);
    }
}