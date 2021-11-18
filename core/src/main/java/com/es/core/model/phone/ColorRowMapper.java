package com.es.core.model.phone;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorRowMapper implements RowMapper<Color> {
    @Override
    public Color mapRow(ResultSet resultSet, int i) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getLong("id"));
        color.setCode(resultSet.getString("code"));
        return color;
    }
}
