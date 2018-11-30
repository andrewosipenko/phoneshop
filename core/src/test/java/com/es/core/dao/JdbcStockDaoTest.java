package com.es.core.dao;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class JdbcStockDaoTest {
    private static final String SQL_QUERY_FOR_INSERT_PHONE = "insert into phones (id, brand, model, price) values (?, ?, ?, ?)";
    private static final String SQL_QUERY_FOR_CLEAR_PHONES = "delete from phones";
    private static final String SQL_QUERY_FOR_SETTING_STOCK = "insert into stocks (phoneId, stock, reserved) values (?,?,?)";
    private static final Integer INITIAL_PHONE_STOCK_VALUE = 5;
    private static final Integer INITIAL_PHONE_RESERVED_VALUE = 1;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private StockDao stockDao;
    private Phone initialPhone = new Phone();

    @Before
    public void clear() {
        initialPhone.setId(999L);
        initialPhone.setBrand("TestBrand");
        initialPhone.setModel("testModel");
        initialPhone.setPrice(BigDecimal.ONE);
        jdbcTemplate.update(SQL_QUERY_FOR_CLEAR_PHONES);
        jdbcTemplate.update(SQL_QUERY_FOR_INSERT_PHONE, initialPhone.getId(), initialPhone.getBrand(), initialPhone.getModel(), initialPhone.getPrice());
        jdbcTemplate.update(SQL_QUERY_FOR_SETTING_STOCK, initialPhone.getId(), INITIAL_PHONE_STOCK_VALUE, INITIAL_PHONE_RESERVED_VALUE);
    }

    @Test
    public void shouldReturnCorrectStock() {
        Integer stock = stockDao.getStockFor(initialPhone.getId());

        assertEquals(INITIAL_PHONE_STOCK_VALUE, stock);
    }
}