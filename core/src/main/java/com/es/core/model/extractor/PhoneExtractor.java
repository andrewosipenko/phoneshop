package com.es.core.model.extractor;

import com.es.core.model.ColorModel;
import com.es.core.model.PhoneModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneExtractor implements ResultSetExtractor<List<PhoneModel>> {

    @Override
    public List<PhoneModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<PhoneModel> phoneList = new ArrayList();
        while (resultSet.next()) {
            PhoneModel phone = new PhoneModel();

            phone.setId(resultSet.getLong("id"));
            phone.setBrand(resultSet.getString("brand"));
            phone.setModel(resultSet.getString("model"));
            phone.setPrice(resultSet.getBigDecimal("price"));
            phone.setDisplaySizeInches(resultSet.getBigDecimal("displaySizeInches"));
            phone.setImageUrl(resultSet.getString("imageUrl"));
            phone.setDescription(resultSet.getString("description"));
            phone.setStock(resultSet.getInt("stock"));

            ColorModel color = new ColorModel(resultSet.getLong("colorId"), resultSet.getString("code"));
            phone.getColors().add(color);

            if (phoneList.contains(phone)) {
                phoneList.get(phoneList.indexOf(phone)).getColors().add(color);
            } else {
                phoneList.add(phone);
            }
        }
        return phoneList;
    }
}
