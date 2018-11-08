package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.JdbcProductDao;
import com.es.core.model.phone.Phone;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class JdbcProductDaoIntTest {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private JdbcProductDao productDao = new JdbcProductDao();
    private Phone phone;
    private Color black;
    private Color white;
    private boolean isInit = false;

    @Before
    public void init() {
        if (!isInit) {
            setInternalState(productDao, "jdbcTemplate", jdbcTemplate);
            black = new Color(800L, "black");
            white = new Color(900L, "white");
            phone = new Phone();
            phone.setId(999L);
            phone.setBrand("TestBrand");
            phone.setModel("testModel");
            phone.setColors(new HashSet<>());
            phone.getColors().add(black);
            phone.getColors().add(white);
            isInit = true;
        }
        jdbcTemplate.update("insert into colors values (?,?)", black.getId(), black.getCode());
        jdbcTemplate.update("insert into colors values (?,?)", white.getId(), white.getCode());
        jdbcTemplate.update("insert into phones (id, brand, model) values (?, ?, ?)", phone.getId(), phone.getBrand(), phone.getModel());
        jdbcTemplate.update("insert into phone2color values (?,?)", phone.getId(), black.getId());
        jdbcTemplate.update("insert into phone2color values (?,?)", phone.getId(), white.getId());
        productDao.afterPropertiesSet();
    }

    @After
    public void clear() {
        jdbcTemplate.update("delete from colors");
        jdbcTemplate.update("delete from phones");
        jdbcTemplate.update("delete from phone2color");
    }

    @Test
    public void testGet() {
        Phone testPhone = productDao.get(phone.getId()).get();
        Assert.assertEquals(phone.getBrand(), testPhone.getBrand());
        Assert.assertEquals(phone.getColors(), testPhone.getColors());
    }

    @Test
    public void testFindOne() {
        List<Phone> phones = productDao.findAll(0,1);
        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(phone.getBrand(), phones.get(0).getBrand());
        Assert.assertEquals(phone.getColors(), phones.get(0).getColors());
    }

    @Test
    public void testFindTwo() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrand2");
        phone2.setModel("testModel2");
        jdbcTemplate.update("insert into phones (id, brand, model) values (?, ?, ?)", phone2.getId(), phone2.getBrand(), phone2.getModel());
        List<Phone> phones = productDao.findAll(0,2);
        Assert.assertEquals(2, phones.size());
        Assert.assertEquals(phone2.getBrand(), phones.get(0).getBrand());
    }

    @Test
    public void testSave() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        productDao.save(phone2);
        Assert.assertEquals(jdbcTemplate.queryForObject("select * from phones where id="+phone2.getId(), new BeanPropertyRowMapper<>(Phone.class)).getBrand(),phone2.getBrand());
    }

    @Test
    public void testSaveWithWrongExpect() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        productDao.save(phone2);
        Assert.assertNotEquals(jdbcTemplate.queryForObject("select * from phones where id="+phone2.getId(), new BeanPropertyRowMapper<>(Phone.class)).getBrand(),"WrongBrand");
    }

    @Test
    public void testRewriteSave() {
        Phone phone2 = new Phone();
        phone2.setId(phone.getId());
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        productDao.save(phone2);
        List<Phone> phones = jdbcTemplate.query("select * from phones", new BeanPropertyRowMapper<>(Phone.class));
        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(phone2.getBrand(), phones.get(0).getBrand());
    }

    @Test
    public void testSaveWithoutId() {
        Phone phone2 = new Phone();
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        productDao.save(phone2);
        List<Phone> phones = jdbcTemplate.query("select * from phones", new BeanPropertyRowMapper<>(Phone.class));
        Assert.assertEquals(2, phones.size());
        Assert.assertEquals(phone2.getBrand(), phones.get(1).getBrand());
    }

    @Test
    public void testDelete() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrandDelete");
        phone2.setModel("testModelDelete");
        productDao.delete(phone.getId());
        jdbcTemplate.update("insert into phones (id, brand, model) values (?, ?, ?)", phone2.getId(), phone2.getBrand(), phone2.getModel());
        List<Phone> phones = jdbcTemplate.query("select * from phones", new BeanPropertyRowMapper<>(Phone.class));
        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(phone2.getBrand(), phones.get(0).getBrand());
    }

    public static void setInternalState(Object c, String field, Object value) {
        try {
            Field f = c.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(c, value);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException er) {
            er.printStackTrace();
        }
    }

    public static Object getInternalState(Object c, String field) {
        try {
            Field f = c.getClass().getDeclaredField(field);
            f.setAccessible(true);
            return f.get(c);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
            return null;
        } catch (IllegalAccessException er) {
            er.printStackTrace();
            return null;
        }
    }
}