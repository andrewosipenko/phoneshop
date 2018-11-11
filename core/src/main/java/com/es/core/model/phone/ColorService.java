package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColorService {
    private static final String SQL_QUERY_FOR_GETTING_ALL_COLORS = "select * from colors";

    public static Map<Long, Color> getColors(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(SQL_QUERY_FOR_GETTING_ALL_COLORS, new BeanPropertyRowMapper<>(Color.class)).stream().collect(Collectors.toMap(Color::getId, (c) -> c));
    }
}
