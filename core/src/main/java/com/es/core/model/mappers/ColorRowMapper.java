package com.es.core.model.mappers;


import com.es.core.model.phone.Color;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ColorRowMapper implements RowMapper<Color> {
    @Override
    public Color mapRow(ResultSet resultSet, int i) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getLong("colorId"));
        color.setCode(resultSet.getString("code"));
        return color;
    }
}
