package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.JdbcProductDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.*;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class JdbcProductDaoTest {
    private static final String SQL_QUERY_FOR_INSERT_COLOR = "insert into colors values (?,?)";
    private static final String SQL_QUERY_FOR_INSERT_PHONE = "insert into phones (id, brand, model) values (?, ?, ?)";
    private static final String SQL_QUERY_FOR_BINDING_PHONE_AND_COLOR = "insert into phone2color values (?,?)";
    private static final String SQL_QUERY_FOR_CLEAR_COLORS = "delete from colors";
    private static final String SQL_QUERY_FOR_CLEAR_PHONES = "delete from phones";
    private static final String SQL_QUERY_FOR_CLEAR_PHONE2COLOR = "delete from phone2color";
    private static final String SQL_QUERY_FOR_GETTING_ONE_PHONE = "select * from phones where id=?";
    private static final String SQL_QUERY_FOR_GETTING_PHONES = "select * from phones";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private PhoneDao productDao;
    private Phone initialPhone = new Phone();
    private Phone expectedPhone;
    private Color black = new Color(800L, "black");
    private Color white = new Color(900L, "white");
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);

    @PostConstruct
    private void init() {
        initialPhone.setId(999L);
        initialPhone.setBrand("TestBrand");
        initialPhone.setModel("testModel");
        initialPhone.setColors(new HashSet<>());
        initialPhone.getColors().add(black);
        initialPhone.getColors().add(white);
    }

    @Before
    public void clear() {
        jdbcTemplate.update(SQL_QUERY_FOR_CLEAR_COLORS);
        jdbcTemplate.update(SQL_QUERY_FOR_CLEAR_PHONES);
        jdbcTemplate.update(SQL_QUERY_FOR_CLEAR_PHONE2COLOR);
        expectedPhone = new Phone();
        expectedPhone.setBrand("TestBrandSave");
        expectedPhone.setModel("testModelSave");
        jdbcTemplate.update(SQL_QUERY_FOR_INSERT_COLOR, black.getId(), black.getCode());
        jdbcTemplate.update(SQL_QUERY_FOR_INSERT_COLOR, white.getId(), white.getCode());
        jdbcTemplate.update(SQL_QUERY_FOR_INSERT_PHONE, initialPhone.getId(),
                        initialPhone.getBrand(), initialPhone.getModel());
        jdbcTemplate.update(SQL_QUERY_FOR_BINDING_PHONE_AND_COLOR, initialPhone.getId(), black.getId());
        jdbcTemplate.update(SQL_QUERY_FOR_BINDING_PHONE_AND_COLOR, initialPhone.getId(), white.getId());
        ((JdbcProductDao)productDao).init();
    }

    @Test
    public void shouldGet() {
        Phone testPhone = productDao.get(initialPhone.getId()).get();

        assertEquals(initialPhone.getBrand(), testPhone.getBrand());
        assertEquals(initialPhone.getColors(), testPhone.getColors());
    }

    @Test
    public void shouldFindOne() {
        List<Phone> phones = productDao.findAll(0, 1);

        assertEquals(1, phones.size());
        assertEquals(initialPhone.getBrand(), phones.get(0).getBrand());
        assertEquals(initialPhone.getColors(), phones.get(0).getColors());
    }

    @Test
    public void shouldFindTwo() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrand2");
        phone2.setModel("testModel2");
        jdbcTemplate.update(SQL_QUERY_FOR_INSERT_PHONE, phone2.getId(), phone2.getBrand(), phone2.getModel());

        List<Phone> phones = productDao.findAll(0, 2);

        assertEquals(2, phones.size());
        assertEquals(phone2.getBrand(), phones.get(0).getBrand());
    }

    @Test
    public void shouldSave() {
        expectedPhone.setId(998L);

        productDao.save(expectedPhone);

        Phone actualPhone = jdbcTemplate.queryForObject(SQL_QUERY_FOR_GETTING_ONE_PHONE,
                        ((resultSet, i) -> phoneBeanPropertyRowMapper.mapRow(resultSet, 0)),
                                expectedPhone.getId());
        assertEquals(actualPhone.getBrand(), expectedPhone.getBrand());
    }

    @Test
    public void shouldRewriteSave() {
        expectedPhone.setId(initialPhone.getId());

        productDao.save(expectedPhone);

        List<Phone> phones = jdbcTemplate.query(SQL_QUERY_FOR_GETTING_PHONES, phoneBeanPropertyRowMapper);
        assertEquals(1, phones.size());
        assertEquals(expectedPhone.getBrand(), phones.get(0).getBrand());
    }

    @Test
    public void shouldSaveWithoutId() {
        productDao.save(expectedPhone);

        List<Phone> phones = jdbcTemplate.query(SQL_QUERY_FOR_GETTING_PHONES, phoneBeanPropertyRowMapper);
        assertEquals(2, phones.size());
        assertEquals(expectedPhone.getBrand(), phones.get(1).getBrand());
    }
}