package com.es.core.dao.mappers;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PhoneRowMapper extends RowCountCallbackHandler {
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
    private List<Phone> phones;
    private Map<Long, Color> colors;

    public PhoneRowMapper(List<Phone> phones, Map<Long, Color> colors) {
        this.phones = phones;
        this.colors = colors;
    }

    @Override
    protected void processRow(ResultSet resultSet, int rowNum) throws SQLException {
        long phoneId = resultSet.getLong("id");
        Phone phone = phones.stream().filter(p -> p.getId().equals(phoneId)).findFirst().orElse(phoneBeanPropertyRowMapper.mapRow(resultSet, rowNum));
        if (phone.getColors().isEmpty()) {
            phone.setColors(new HashSet<>());
        }
        phone.getColors().add(colors.get(resultSet.getLong("colorId")));
        if (!phones.contains(phone)) {
            phones.add(phone);
        }
    }
}
