package com.es.core.model.phone.mappers;

import com.es.core.model.phone.Color;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ColorExtractor implements ResultSetExtractor<Set<Color>> {

    @Override
    public Set<Color> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Set<Color> colors = new HashSet<>();

        while (resultSet.next()){
            Color newColor = new Color();
            newColor.setCode(resultSet.getString("code"));
            newColor.setId(resultSet.getLong("id"));
            colors.add(newColor);
        }

        return colors;
    }
}
