package com.es.core.extractor.phone;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.util.PhoneCreatorFromResultSet;
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
    private final static Long ZERO_VALUE = 0L;

    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phones = new ArrayList<>();
        Phone phone;

        if (resultSet.next()) {
            phone = createNewPhone(resultSet, phones);
            addColorToPhone(resultSet, phone);

            while (resultSet.next()) {
                Long phoneId = resultSet.getLong(PHONE_ID_PARAMETER);
                if (phone.getId().equals(phoneId)) {
                    addColorToPhone(resultSet, phone);
                } else {
                    phone = createNewPhone(resultSet, phones);
                    addColorToPhone(resultSet, phone);
                }
            }
        }

        return phones;
    }

    private void addColorToPhone(ResultSet resultSet, Phone phone) throws SQLException {
        if (!ZERO_VALUE.equals(resultSet.getLong(COLOR_ID_PARAMETER))) {
            Set<Color> colors = phone.getColors();
            colors.add(PhoneCreatorFromResultSet.createColor(resultSet));
            phone.setColors(colors);
        }
    }

    private Phone createNewPhone(ResultSet resultSet, List<Phone> phones) throws SQLException {
        Phone phone = PhoneCreatorFromResultSet.createPhone(resultSet);
        phone.setColors(new HashSet<>());
        phones.add(phone);
        return phone;
    }
}
