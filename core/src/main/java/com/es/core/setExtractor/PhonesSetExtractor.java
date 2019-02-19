package com.es.core.setExtractor;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.util.CreatorFromResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhonesSetExtractor implements ResultSetExtractor<List<Phone>> {
    private final static String PHONE_ID_PARAMETER = "id";
    private final static String COLOR_ID_PARAMETER = "colorId";
    private final static Long NULL_VALUE = 0L;

    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phones = new ArrayList<>();
        Phone phone = null;
        Set<Color> colors = new HashSet<>();
        boolean isAddPhone = false;

        if (resultSet.next()) {
            phone = CreatorFromResultSet.createPhone(resultSet);
            if (!NULL_VALUE.equals(resultSet.getLong(COLOR_ID_PARAMETER))) {
                colors.add(CreatorFromResultSet.createColor(resultSet));
            }
            isAddPhone = true;
        }

        while (resultSet.next()) {
            Long phoneId = resultSet.getLong(PHONE_ID_PARAMETER);
            if (phone.getId().equals(phoneId)) {
                if (!NULL_VALUE.equals(resultSet.getLong(COLOR_ID_PARAMETER))) {
                    colors.add(CreatorFromResultSet.createColor(resultSet));
                }
                isAddPhone = false;
            } else {
                isAddPhone = true;
                phone.setColors(colors);
                phones.add(phone);
                colors = new HashSet<>();
                phone = CreatorFromResultSet.createPhone(resultSet);
            }
        }

        if (isAddPhone) {
            phone.setColors(colors);
            phones.add(phone);
        }

        return phones;
    }
}
