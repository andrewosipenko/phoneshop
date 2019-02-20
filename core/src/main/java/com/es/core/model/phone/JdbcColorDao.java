package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.List;

@Component
public class JdbcColorDao implements ColorDao {
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String SQL_GET_COLORS_BY_ID = "select * from phone2color inner join colors on (phone2color.colorId = colors.id) " +
            "where phone2color.phoneId = :id";

    private  String SQL_SAVE_COLORS = "insert into phone2color (phoneId, colorId) values (:phoneId, :colorId)";

    private String SQL_SAVE_COLOR = "insert into colors (code) values (:code)";

    @Override
    public Set<Color> get(Long key) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", key);
        return new HashSet<Color>((Collection<? extends Color>) namedParameterJdbcTemplate.query(SQL_GET_COLORS_BY_ID, namedParameters, new BeanPropertyRowMapper(Color.class)));
    }

    @Override
    public void save(Color color) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource("code", color.getCode());
        try {
            int status = namedParameterJdbcTemplate.update(SQL_SAVE_COLOR, namedParameters, keyHolder);
            if (status != 0) {
                color.setId(keyHolder.getKey().longValue());
            }
        } catch (DataAccessException ex) {  }
    }

    @Override
    public void save(Set<Color> colors, Long key) {
        for (Color color : colors) {
            save(color);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("phoneId", key);
            paramMap.put("colorId", color.getId());
            try {
                namedParameterJdbcTemplate.update(SQL_SAVE_COLORS, paramMap);
            } catch (DataAccessException ex) {  }
        }
    }

    @Override
    public List<Color> findAll(int offset, int limit) {
        return null;
    }
}
