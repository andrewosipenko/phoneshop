package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.JdbcProductDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class JdbcProductDaoIntTest {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private PhoneDao productDao;
    private Phone phone;
    private Color black;
    private Color white;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper;
    private boolean isInit = false;
    private PreparedStatement statementForInsertColor;
    private PreparedStatement statementForInsertPhone;
    private PreparedStatement statementForBindingPhoneAndColor;
    private PreparedStatement statementForClearColors;
    private PreparedStatement statementForClearPhones;
    private PreparedStatement statementForClearPhone2Color;
    private PreparedStatement statementForGettingOnePhone;
    private PreparedStatement statementForGettingPhones;

    @Before
    public void init() {
        if (!isInit) {
            black = new Color(800L, "black");
            white = new Color(900L, "white");
            phone = new Phone();
            phone.setId(999L);
            phone.setBrand("TestBrand");
            phone.setModel("testModel");
            phone.setColors(new HashSet<>());
            phone.getColors().add(black);
            phone.getColors().add(white);
            try {
                setValuesForStatements();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
            isInit = true;
        }
        try {
            setParametersForStatementForInsertColorAndExecute(black);
            setParametersForStatementForInsertColorAndExecute(white);
            setParameterForStatementForInsertPhoneAndExecute(phone);
            statementForBindingPhoneAndColor.setLong(1, phone.getId());
            statementForBindingPhoneAndColor.setLong(2, black.getId());
            statementForBindingPhoneAndColor.executeUpdate();
            statementForBindingPhoneAndColor.setLong(2, white.getId());
            statementForBindingPhoneAndColor.executeUpdate();
            ((JdbcProductDao) productDao).afterPropertiesSet();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @After
    public void clear() {
        try {
            statementForClearColors.executeUpdate();
            statementForClearPhones.executeUpdate();
            statementForClearPhone2Color.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldGet() {
        Phone testPhone = productDao.get(phone.getId()).get();

        Assert.assertEquals(phone.getBrand(), testPhone.getBrand());
        Assert.assertEquals(phone.getColors(), testPhone.getColors());
    }

    @Test
    public void shouldFindOne() {
        List<Phone> phones = productDao.findAll(0,1);
        Assert.assertEquals(1, phones.size());
        Assert.assertEquals(phone.getBrand(), phones.get(0).getBrand());
        Assert.assertEquals(phone.getColors(), phones.get(0).getColors());
    }

    @Test
    public void shouldFindTwo() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrand2");
        phone2.setModel("testModel2");
        try {
            setParameterForStatementForInsertPhoneAndExecute(phone2);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        List<Phone> phones = productDao.findAll(0,2);

        Assert.assertEquals(2, phones.size());
        Assert.assertEquals(phone2.getBrand(), phones.get(0).getBrand());
    }

    @Test
    public void shouldSave() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        try {
            statementForGettingOnePhone.setLong(1, phone2.getId());

            productDao.save(phone2);

            ResultSet resultSet = statementForGettingOnePhone.executeQuery();
            resultSet.next();
            Assert.assertEquals(phoneBeanPropertyRowMapper.mapRow(resultSet, 0).getBrand(), phone2.getBrand());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldSaveWithWrongExpect() {
        Phone phone2 = new Phone();
        phone2.setId(998L);
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        try {
            statementForGettingOnePhone.setLong(1, phone2.getId());

            productDao.save(phone2);

            ResultSet resultSet = statementForGettingOnePhone.executeQuery();
            resultSet.next();
            Assert.assertNotEquals(phoneBeanPropertyRowMapper.mapRow(resultSet, 0).getBrand(), "WrongBrand");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldRewriteSave() {
        Phone phone2 = new Phone();
        phone2.setId(phone.getId());
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        List<Phone> phones = new ArrayList<>();

        productDao.save(phone2);

        try {
            ResultSet resultSet = statementForGettingPhones.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                phones.add(phoneBeanPropertyRowMapper.mapRow(resultSet, i++));
            }
            Assert.assertEquals(1, phones.size());
            Assert.assertEquals(phone2.getBrand(), phones.get(0).getBrand());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldSaveWithoutId() {
        Phone phone2 = new Phone();
        phone2.setBrand("TestBrandSave");
        phone2.setModel("testModelSave");
        List<Phone> phones = new ArrayList<>();

        productDao.save(phone2);

        try {
            ResultSet resultSet = statementForGettingPhones.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                phones.add(phoneBeanPropertyRowMapper.mapRow(resultSet, i++));
            }
            Assert.assertEquals(2, phones.size());
            Assert.assertEquals(phone2.getBrand(), phones.get(1).getBrand());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void setValuesForStatements() throws SQLException {
    Connection connection = jdbcTemplate.getDataSource().getConnection();
    statementForInsertColor = connection.prepareStatement("insert into colors values (?,?)");
    statementForInsertPhone = connection.prepareStatement("insert into phones (id, brand, model) values (?, ?, ?)");
    statementForBindingPhoneAndColor = connection.prepareStatement("insert into phone2color values (?,?)");
    statementForClearColors = connection.prepareStatement("delete from colors");
    statementForClearPhones = connection.prepareStatement("delete from phones");
    statementForClearPhone2Color = connection.prepareStatement("delete from phone2color");
    statementForGettingOnePhone = connection.prepareStatement("select * from phones where id=?");
    statementForGettingPhones = connection.prepareStatement("select * from phones");
    }

    private void setParametersForStatementForInsertColorAndExecute(Color color) throws SQLException {
        statementForInsertColor.setLong(1, color.getId());
        statementForInsertColor.setString(2, color.getCode());
        statementForInsertColor.executeUpdate();
    }

    private void setParameterForStatementForInsertPhoneAndExecute(Phone phone) throws SQLException {
        statementForInsertPhone.setLong(1, phone.getId());
        statementForInsertPhone.setString(2, phone.getBrand());
        statementForInsertPhone.setString(3, phone.getModel());
        statementForInsertPhone.executeUpdate();
    }
}