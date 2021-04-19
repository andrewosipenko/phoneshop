package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {
    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phones = new ArrayList<>();
        while (resultSet.next()) {
            Phone phone = new Phone();
            Long id = resultSet.getLong("id");
            phone.setId(id);
            Color color = findColor(resultSet);
            Optional<Phone> optionalPhone = phones.stream().filter(p -> p.getId().equals(id)).findAny();
            if (optionalPhone.isPresent()) {
                int phoneIndex = phones.stream()
                        .map(Phone::getId)
                        .collect(Collectors.toList())
                        .indexOf(id);
                if (color.getCode() != null) {
                    phones.get(phoneIndex).getColors().add(color);
                }
            } else {
                setFieldValues(phone, resultSet);
                if (color.getCode() != null) {
                    Set<Color> colorSet = new HashSet<>(Arrays.asList(color));
                    phone.setColors(colorSet);
                }
                phones.add(phone);
            }
        }
        return phones;
    }

    private void setFieldValues(Phone phone, ResultSet resultSet) throws SQLException, DataAccessException {
        phone.setBrand(resultSet.getString("brand"));
        phone.setModel(resultSet.getString("model"));
        phone.setPrice(resultSet.getBigDecimal("price"));
        phone.setDisplaySizeInches(resultSet.getBigDecimal("displaySizeInches"));
        phone.setWeightGr(resultSet.getInt("weightGr"));
        phone.setLengthMm(resultSet.getBigDecimal("lengthMm"));
        phone.setWidthMm(resultSet.getBigDecimal("widthMm"));
        phone.setHeightMm(resultSet.getBigDecimal("heightMm"));
        phone.setAnnounced(resultSet.getDate("announced"));
        phone.setDeviceType(resultSet.getString("deviceType"));
        phone.setOs(resultSet.getString("os"));
        phone.setDisplayResolution(resultSet.getString("displayResolution"));
        phone.setPixelDensity(resultSet.getInt("pixelDensity"));
        phone.setDisplayTechnology(resultSet.getString("displayTechnology"));
        phone.setBackCameraMegapixels(resultSet.getBigDecimal("backCameraMegapixels"));
        phone.setFrontCameraMegapixels(resultSet.getBigDecimal("frontCameraMegapixels"));
        phone.setRamGb(resultSet.getBigDecimal("ramGb"));
        phone.setInternalStorageGb(resultSet.getBigDecimal("internalStorageGb"));
        phone.setBatteryCapacityMah(resultSet.getInt("batteryCapacityMah"));
        phone.setTalkTimeHours(resultSet.getBigDecimal("talkTimeHours"));
        phone.setStandByTimeHours(resultSet.getBigDecimal("standByTimeHours"));
        phone.setBluetooth(resultSet.getString("bluetooth"));
        phone.setPositioning(resultSet.getString("positioning"));
        phone.setImageUrl(resultSet.getString("imageUrl"));
        phone.setDescription(resultSet.getString("description"));
    }

    private Color findColor(ResultSet resultSet) throws SQLException, DataAccessException {
        Long colorId = resultSet.getLong("colorId");
        String colorCode = resultSet.getString("code");
        return new Color(colorId, colorCode);
    }
}