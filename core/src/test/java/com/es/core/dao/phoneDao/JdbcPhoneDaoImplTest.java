package com.es.core.dao.phoneDao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
class JdbcPhoneDaoImplTest {

    private Phone phone;

    @Mock
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper;

    @Mock
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper;

    @Mock
    private SimpleJdbcInsert phoneSimpleJdbcInsert;

    @Mock
    private JdbcTemplate jdbcTemplate;


    @Before
    private void initPhone() {

    }

    private void getTest() {

    }

}