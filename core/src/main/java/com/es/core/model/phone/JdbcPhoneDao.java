package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
       String sql = "select * from phones where id = ?";
       try {
           Phone phone = (Phone) jdbcTemplate.queryForObject(sql, new Object[]{key}, new BeanPropertyRowMapper(Phone.class));
           return Optional.of(phone);
       }
       catch(EmptyResultDataAccessException e) {
           return Optional.empty();
       }
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }

    public List<Color> getPhoneColors(final Long key){
        String sql = "select colorId, colors.code from phone2color join colors on colors.id = phone2color.colorId where phoneId = ?";
        return jdbcTemplate.query(sql, new Object[]{key}, new RowMapper<Color>(){
            @Override
            public Color mapRow(ResultSet rs, int rowNumber) throws SQLException{
                Color color = new Color();
                color.setId(rs.getLong(1));
                color.setCode(rs.getString(2));
                return color;
            }
        });
    }
}
