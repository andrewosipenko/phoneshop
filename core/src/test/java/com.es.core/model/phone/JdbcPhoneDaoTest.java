package com.es.core.model.phone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Dmitry Timofeev on 13.11.18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "")
@SpringBootTest
public class JdbcPhoneDaoTest {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private JdbcPhoneDao jdbcPhoneDao;

    private Phone phone;
    private Phone newPhone;
    private Color red = new Color(1004L, "Red");
    private Color green = new Color(1007L, "Green");
    private Color blue = new Color(1003L, "Blue");

    @Before
    public void init() {
        phone = new Phone();
        phone.setBrand("Brand");
        phone.setModel("Model");

        newPhone = new Phone();
        newPhone.setId(999L);
        newPhone.setBrand("New Brand");
        newPhone.setModel("New Model");
        this.jdbcTemplate.update("insert into phones (id, brand, model) values (?, ?, ?)",
                newPhone.getId(),
                newPhone.getBrand(),
                newPhone.getModel());
    }

    @After
    public void destroy() {
        phone = null;
    }

    @Test
    public void saveTest() {
        long oldCount = this.jdbcTemplate.queryForObject("select count(*) from phones", long.class);
        long oldMaxId = this.jdbcTemplate.queryForObject("select max(id) from phones", long.class);
        this.phoneDao.save(phone);
        long newCount = this.jdbcTemplate.queryForObject("select count(*) from phones", long.class);
        long newMaxId = this.jdbcTemplate.queryForObject("select max(id) from phones", long.class);
        Assert.assertEquals(oldMaxId + 1, newMaxId);
        Assert.assertEquals(oldCount + 1, newCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionNullBrand() {
        phone.setBrand(null);
        this.phoneDao.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionNullModel() {
        phone.setModel(null);
        this.phoneDao.save(phone);
    }

    @Test
    public void finaFirstTest() {
        List<Phone> phones = this.phoneDao.findAll(0, 1);
        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(newPhone.getBrand(), phones.get(0).getBrand());
        Assert.assertEquals(newPhone.getBrand(), phones.get(0).getModel());
    }

    @Test
    public void getTest() {
        phone = this.phoneDao.get(999L).get();
        Assert.assertEquals(newPhone.getId(), phone.getId());
        Assert.assertEquals(newPhone.getBrand(), phone.getBrand());
        Assert.assertEquals(newPhone.getModel(), phone.getModel());
    }

    @Test
    public void deleteTest() {
        long oldCount = this.jdbcTemplate.queryForObject("select count(*) from phones", long.class);
        phone.setId(1000L);
        this.jdbcPhoneDao.delete(phone);
        long newCount = this.jdbcTemplate.queryForObject("select count(*) from phones", long.class);
        Assert.assertEquals(oldCount - 1, newCount);
    }
}
