package com.es.core.extractor;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import com.es.core.util.CreatorFromResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneSetExtractor implements ResultSetExtractor<Phone> {
    private final static String COLOR_ID_PARAMETER = "colorId";
    private final static Long NULL_VALUE = 0L;

    @Override
    public Phone extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        PhonesSetExtractor phonesSetExtractor = new PhonesSetExtractor();
        List<Phone> phones = phonesSetExtractor.extractData(resultSet);

        if (phones.size() == 0) {
            throw new IllegalArgumentException();
        }

        return phones.get(0);
    }
}
