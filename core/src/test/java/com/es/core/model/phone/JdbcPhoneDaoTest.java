package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class JdbcPhoneDaoTest {

    @Resource
    private PhoneDao jdbcPhoneDao;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Test
    public void getWithCorrectIdTest() {
        final long correctId = 1001;
        Phone phone = jdbcPhoneDao.get(correctId).get();
        assertNotNull(phone);
        assertThat(phone.getId(), is(correctId));
    }

    @Test
    public void getWithInvalidIdTest() {
        final long invalidId = -2;
        assertThat(jdbcPhoneDao.get(invalidId).isPresent(), is(false));
    }

    @Rollback(true)
    @Test
    public void saveTest() {
        int tableSizeBeforeInsert = getCount();
        jdbcPhoneDao.save(createPhone());
        int tableSizeAfterInsert = getCount();
        assertThat(tableSizeAfterInsert, is(tableSizeBeforeInsert + 1));
    }

    @Test
    public void findAllTest() {
        int offset = 0;
        int limit = 10;
        List<Phone> phones = jdbcPhoneDao.findAll(offset, limit);
        assertThat(phones.size(), is(limit - offset));
    }

    public int getCount() {
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones", Integer.class);
    }

    public Phone createPhone() {
        Phone phone = new Phone();
        phone.setBrand("as");
        phone.setModel("qw");
        return phone;
    }
}