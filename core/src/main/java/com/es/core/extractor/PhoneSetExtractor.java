package com.es.core.extractor;

import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PhoneSetExtractor implements ResultSetExtractor<Phone> {
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
