package com.es.core.dao.phoneDao;

import com.es.core.model.entity.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDaoImpl implements PhoneDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPhoneDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Phone> get(final Long key) {
        throw new UnsupportedOperationException("TODO");
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset ? limit ?", new Object[]{offset, limit}, new BeanPropertyRowMapper(Phone.class));
    }
}
