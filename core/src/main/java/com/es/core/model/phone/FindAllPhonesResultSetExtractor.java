package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class FindAllPhonesResultSetExtractor implements ResultSetExtractor<List<Phone>> {

    @Override
    public List<Phone> extractData(ResultSet rs) throws SQLException, DataAccessException {
        LinkedHashMap<Long, Phone> phones = new LinkedHashMap<>();
        if(rs.next()){
            phones.put(rs.getLong("id"), getPhone(rs));
        }
        Long phoneId;

        while(rs.next()){
            phoneId = rs.getLong("id");

            if (phones.containsKey(phoneId)) {
                addColor(phones.get(phoneId).getColors(), rs);
            } else {
                phones.put(phoneId, getPhone(rs));
            }
        }
        return new LinkedList<>(phones.values());
    }

    private Phone getPhone(ResultSet rs) throws SQLException, DataAccessException{
        Phone phone = new Phone();
        Long phoneId = rs.getLong("id");
        phone.setId(phoneId);
        phone.setImageUrl(rs.getString("imageUrl"));
        phone.setBrand(rs.getString("brand"));
        phone.setModel(rs.getString("model"));
        phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
        phone.setPrice(rs.getBigDecimal("price").setScale(2, RoundingMode.HALF_EVEN));

        Set<Color> colors = new HashSet<>();
        addColor(colors, rs);
        phone.setColors(colors);
        return phone;
    }

    private void addColor(Set<Color> colors, ResultSet rs) throws SQLException, DataAccessException{
        Long colorId = rs.getLong("colorId");
        if (rs.wasNull()) return;

        Color color = new Color();
        color.setId(colorId);
        color.setCode(rs.getString("colorCode"));

        colors.add(color);
    }
}

