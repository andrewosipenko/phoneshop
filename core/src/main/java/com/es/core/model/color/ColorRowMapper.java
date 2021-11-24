package com.es.core.model.color;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorRowMapper implements RowMapper<Color> {

    public static final String ID = "id";
    public static final String CODE = "code";

    @Override
    public Color mapRow(ResultSet resultSet, int i) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getLong(ID));
        color.setCode(resultSet.getString(CODE));
        return color;
    }
}
