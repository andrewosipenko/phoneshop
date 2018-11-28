package com.es.core.model.phone;

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
    private final String codeRedColor = "Red";
    private final long idRedColor = 1004L;
    private final String codeGreenColor = "Green";
    private final long idGreenColor = 1007L;
    private final String codeBlueColor = "Blue";
    private final long idBlueColor = 1003L;
    private final String phoneBrand = "Brand";
    private final String phoneModel = "Model";
    private final String newPhoneBrand = "New Brand";
    private final String newPhoneModel = "New Model";
    private final long newPhoneId = 999L;
    private final long idForDelete = 1000L;

    @Resource
    private JdbcTemplate jdbcTemplateTest;
    @Resource
    private PhoneDao phoneDao;

    private Phone phone;
    private Phone newPhone;
    private Color red = new Color(idRedColor, codeRedColor);
    private Color green = new Color(idGreenColor, codeGreenColor);
    private Color blue = new Color(idBlueColor, codeBlueColor);

    @Before
    public void init() {
        phone = new Phone();
        phone.setBrand(phoneBrand);
        phone.setModel(phoneModel);

        newPhone = new Phone();
        newPhone.setId(newPhoneId);
        newPhone.setBrand(newPhoneBrand);
        newPhone.setModel(newPhoneModel);
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

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionSaveSamePhone() {
        this.phoneDao.save(phone);
        newPhone.setBrand(phoneBrand);
        newPhone.setModel(phoneModel);
        newPhone.setId(phone.getId());

        this.phoneDao.save(newPhone);
    }

    @Test
    public void shouldFindFirstPhone() {
        List<Phone> phones = this.phoneDao.findAll(0, 1);

        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(newPhone.getBrand(), phones.get(0).getBrand());
        Assert.assertEquals(newPhone.getModel(), phones.get(0).getModel());
    }

    @Test
    public void shouldGetPhoneById() {
        phone = this.phoneDao.get(newPhoneId).get();

        Assert.assertEquals(newPhone.getId(), phone.getId());
        Assert.assertEquals(newPhone.getBrand(), phone.getBrand());
        Assert.assertEquals(newPhone.getModel(), phone.getModel());
    }

    @Test
    public void shouldDeletePhone() {
        long oldCount = this.jdbcTemplateTest.queryForObject(SQL_SELECT_COUNT_FROM_PHONES, long.class);
        phone.setId(idForDelete);

        this.phoneDao.delete(phone);
        long newCount = this.jdbcTemplateTest.queryForObject(SQL_SELECT_COUNT_FROM_PHONES, long.class);

        Assert.assertEquals(oldCount - 1, newCount);
    }
}