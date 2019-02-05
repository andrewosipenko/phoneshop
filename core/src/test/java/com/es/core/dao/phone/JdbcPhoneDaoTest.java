package com.es.core.dao.phone;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class JdbcPhoneDaoTest {
    private final String SQL_INSERT_INTO_PHONES = "insert into phones (id, brand, model) values (?, ?, ?)";
    private final String SQL_SELECT_COUNT_FROM_PHONES = "select count(*) from phones";
    private final String SQL_SELECT_MAX_ID_FROM_PHONES = "select max(id) from phones";
    private final String CODE_RED_COLOR = "Red";
    private final long ID_RED_COLOR = 1004L;
    private final String CODE_GREEN_COLOR = "Green";
    private final long ID_GREEN_COLOR = 1007L;
    private final String CODE_BLUE_COLOR = "Blue";
    private final long ID_BLUE_COLOR = 1003L;
    private final String PHONE_BRAND = "Brand";
    private final String PHONE_MODEL = "Model";
    private final String NEW_PHONE_BRAND = "New Brand";
    private final String NEW_PHONE_MODEL = "New Model";
    private final long NEW_PHONE_ID = 999L;
    private final long ID_FOR_DELETE = 1000L;
    private final long REAL_MAX_ID = 8251L;
    private final long REAL_COUNT_PHONES = 16L;
    private final long REAL_NUMBER_AVAILABLE_PHONES = 2L;
    private final String DEFAULT_SEARCH = "";
    private final String DEFAULT_SORT = "brand";
    private final String DEFAULT_DIRECTION = "asc";
    private final long FIRST_AVAILABLE_PHONE_ID = 1003L;
    private final String FIRST_AVAILABLE_PHONE_BRAND = "ARCHOS";
    private final String FIRST_AVAILABLE_PHONE_MODEL = "ARCHOS 101 Oxygen";

    @Resource
    private JdbcTemplate jdbcTemplateTest;
    @Resource
    private PhoneDao phoneDao;

    private Phone phone;
    private Phone newPhone;
    private Color red = new Color(ID_RED_COLOR, CODE_RED_COLOR);
    private Color green = new Color(ID_GREEN_COLOR, CODE_GREEN_COLOR);
    private Color blue = new Color(ID_BLUE_COLOR, CODE_BLUE_COLOR);

    @Before
    public void init() {
        phone = new Phone();
        phone.setBrand(PHONE_BRAND);
        phone.setModel(PHONE_MODEL);

        newPhone = new Phone();
        newPhone.setId(NEW_PHONE_ID);
        newPhone.setBrand(NEW_PHONE_BRAND);
        newPhone.setModel(NEW_PHONE_MODEL);
        this.jdbcTemplateTest.update(SQL_INSERT_INTO_PHONES,
                newPhone.getId(),
                newPhone.getBrand(),
                newPhone.getModel());
    }

    @Test
    public void shouldSavePhone() {
        long oldCount = this.jdbcTemplateTest.queryForObject(SQL_SELECT_COUNT_FROM_PHONES, long.class);
        long oldMaxId = this.jdbcTemplateTest.queryForObject(SQL_SELECT_MAX_ID_FROM_PHONES, long.class);

        this.phoneDao.save(phone);
        long newCount = this.jdbcTemplateTest.queryForObject(SQL_SELECT_COUNT_FROM_PHONES, long.class);
        long newMaxId = this.jdbcTemplateTest.queryForObject(SQL_SELECT_MAX_ID_FROM_PHONES, long.class);

        Assert.assertEquals(oldMaxId + 1, newMaxId);
        Assert.assertEquals(oldCount + 1, newCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionSaveNullPhone() {
        phone = null;

        this.phoneDao.save(phone);
    }

    @Test
    public void shouldGetPhoneById() {
        phone = this.phoneDao.get(NEW_PHONE_ID).get();

        Assert.assertEquals(newPhone.getId(), phone.getId());
        Assert.assertEquals(newPhone.getBrand(), phone.getBrand());
        Assert.assertEquals(newPhone.getModel(), phone.getModel());
    }

    @Test
    public void shouldDeletePhone() {
        long oldCount = this.jdbcTemplateTest.queryForObject(SQL_SELECT_COUNT_FROM_PHONES, long.class);
        phone.setId(ID_FOR_DELETE);

        this.phoneDao.delete(phone);
        long newCount = this.jdbcTemplateTest.queryForObject(SQL_SELECT_COUNT_FROM_PHONES, long.class);

        Assert.assertEquals(oldCount - 1, newCount);
    }

    @Test
    public void shouldGetMaxPhoneId(){
        long maxId = this.phoneDao.getMaxPhoneId();

        Assert.assertEquals(REAL_MAX_ID, maxId);
    }

    @Test
    public void shouldGetCountPhones(){
        long countPhones = this.phoneDao.getCountPhones();

        Assert.assertEquals(REAL_COUNT_PHONES, countPhones);
    }

    @Test
    public void shouldGetNumberAvailablePhones(){
        long numberAvailablePhones = this.phoneDao.getNumberAvailablePhones("");

        Assert.assertEquals(REAL_NUMBER_AVAILABLE_PHONES, numberAvailablePhones);
    }

    @Test
    public void shouldFindFirstAvailablePhone(){
        List<Phone> phones = this.phoneDao.findAll(0, 1, DEFAULT_SEARCH,DEFAULT_SORT, DEFAULT_DIRECTION);

        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(FIRST_AVAILABLE_PHONE_BRAND, phones.get(0).getBrand());
        Assert.assertEquals(FIRST_AVAILABLE_PHONE_MODEL, phones.get(0).getModel());
    }

    @Test
    public void shouldContainsPhone(){
        boolean isContains = this.phoneDao.contains(FIRST_AVAILABLE_PHONE_ID);

        Assert.assertTrue(isContains);
    }
}