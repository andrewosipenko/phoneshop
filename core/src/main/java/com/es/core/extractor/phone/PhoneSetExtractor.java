package com.es.core.extractor.phone;

import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class PhoneSetExtractor implements ResultSetExtractor<Phone> {
    @Resource
    private PhonesSetExtractor phonesSetExtractor;

    @Override
    public Phone extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phones = phonesSetExtractor.extractData(resultSet);

        if (phones.size() == 0) {
            throw new IllegalArgumentException();
        }

        return phones.get(0);
    }
}
