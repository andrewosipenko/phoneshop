package com.es.core.model;

import com.es.core.model.mappers.ColorRowMapper;
import com.es.core.model.phone.Color;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JdbcColorDao implements ColorDao {

    private static final String SELECT_COLORS_OF_PHONE = "SELECT colorId, code FROM phone2color JOIN colors ON " +
            "phone2color.colorId=colors.id WHERE phoneId=?";

    @Resource
    private JdbcTemplate jdbctemplate;

    @Resource
    private ColorRowMapper colorRowMapper;

    @Override
    public Set<Color> loadColorsOfPhoneByID(long id) {
        return new HashSet(jdbctemplate.query(SELECT_COLORS_OF_PHONE, new Object[]{ id }, colorRowMapper));
    }
}
