package com.es.core.model.phone;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneRowMapper implements RowMapper<Phone> {

    private List<Phone> filledPhones;

    public PhoneRowMapper() {
        filledPhones = new ArrayList<>();
    }

    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("id"));
        phone.setBrand(resultSet.getString("brand"));
        phone.setModel(resultSet.getString("model"));
        phone.setPrice(resultSet.getBigDecimal("price"));
        phone.setImageUrl(resultSet.getString("imageUrl"));
        Color color = new Color(resultSet.getLong("id"), resultSet.getString("code"));
        phone.getColors().add(color);
        int index = filledPhones.indexOf(phone);
        if(index >= 0) {
            filledPhones.get(filledPhones.indexOf(phone)).getColors().add(color);
        } else {
            filledPhones.add(phone);
        }
        return phone;
    }

    public List<Phone> getFilledPhones() {
        return filledPhones;
    }
}
