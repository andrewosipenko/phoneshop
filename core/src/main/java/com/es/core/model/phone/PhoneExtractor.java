package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneExtractor implements ResultSetExtractor<List<Phone>> {

    @Override
    public List<Phone> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Phone> phones = new ArrayList<>();
        while(rs.next()){
            long id = rs.getLong("id");
            Phone phone = phones.stream()
                    .filter(existingPhone -> id == existingPhone.getId())
                    .findFirst()
                    .orElse(null);
            if(phone == null){
                phone = new BeanPropertyRowMapper<>(Phone.class).mapRow(rs, rs.getRow());
                phone.setId(id);
                phones.add(phone);
            }

            String colorCode = rs.getString("code");
            long colorId = rs.getLong("colorId");

            if(colorId != 0 && colorCode != null) {
                Color color = new ColorMapper().mapRow(rs, rs.getRow());
                phone.getColors().add(color);
            }
        }
        return phones;
    }
}
