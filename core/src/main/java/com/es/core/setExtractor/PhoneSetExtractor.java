package com.es.core.setExtractor;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.util.CreatorFromResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PhoneSetExtractor implements ResultSetExtractor<Phone> {
    private final static String COLOR_ID_PARAMETER = "colorId";
    private final static Long NULL_VALUE = 0L;

    @Override
    public Phone extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Phone phone = new Phone();
        Set<Color> colors = new HashSet<>();
        if (resultSet.next()) {
            phone = CreatorFromResultSet.createPhone(resultSet);

            while (resultSet.next()) {
                if (!NULL_VALUE.equals(resultSet.getLong(COLOR_ID_PARAMETER))) {
                    colors.add(CreatorFromResultSet.createColor(resultSet));
                }
            }

        }
        phone.setColors(colors);
        return phone;
    }
}
