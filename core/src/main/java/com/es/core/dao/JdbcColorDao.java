package com.es.core.dao;

import com.es.core.model.phone.Color;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class JdbcColorDao implements ColorDao {
    private static final String SELECT_ALL_COLORS = "select * from colors";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Color> getAllColors() {
        return jdbcTemplate.query(SELECT_ALL_COLORS, new BeanPropertyRowMapper<>(Color.class));
    }
}
