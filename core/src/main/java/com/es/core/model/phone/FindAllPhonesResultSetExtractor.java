package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class FindAllPhonesResultSetExtractor implements ResultSetExtractor<List<Phone>> {

    @Override
    public List<Phone> extractData(ResultSet rs) throws SQLException, DataAccessException {
        LinkedList<Phone> phones = new LinkedList<>();
        if(rs.next()){
            phones.add(getPhone(rs));
        }
        Long phoneId;
        Optional<Phone> phone;
        while(rs.next()){
            phoneId = rs.getLong("id");
            phone = lookFor(phoneId, phones);

            if (phone.isPresent()) {
                phone.get()
                        .getColors()
                        .add(getColor(rs));
            } else {
                phones.add(getPhone(rs));
            }
        }
        return phones;
    }

    private Optional<Phone> lookFor(Long id, LinkedList<Phone> phones){
        return phones.stream()
                .filter(p-> p.getId().equals(id))
                .findFirst();
    }

    private Phone getPhone(ResultSet rs) throws SQLException, DataAccessException{
        Phone phone = new Phone();
        Long phoneId = rs.getLong("id");
        phone.setId(phoneId);
        phone.setImageUrl(rs.getString("imageUrl"));
        phone.setBrand(rs.getString("brand"));
        phone.setModel(rs.getString("model"));
        phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
        phone.setPrice(rs.getBigDecimal("price"));

        Set<Color> colors = new HashSet<>();
        colors.add(getColor(rs));
        phone.setColors(colors);
        return phone;
    }

    private Color getColor(ResultSet rs) throws SQLException, DataAccessException{
        Color color = new Color();
        color.setCode(rs.getString("colorCode"));
        color.setId(rs.getLong("colorId"));
        return color;
    }
}

