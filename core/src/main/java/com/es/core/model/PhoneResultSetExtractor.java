package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {
    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phoneList = new ArrayList<>();
        Phone phone = null;
        Set<Color> colors = null;
        while (resultSet.next()) {
            if (phone == null || phone.getId() != resultSet.getLong("id")) {
                phone = new Phone();
                colors = new HashSet<>();
                phone.setColors(colors);
                phoneList.add(phone);
                setPhoneProps(phone, resultSet);
            }
            if (resultSet.getString("colorCode") != null) {
                Color color = new Color();
                color.setId(resultSet.getLong("colorId"));
                color.setCode(resultSet.getString("colorCode"));
                colors.add(color);
            }
        }
        return phoneList;
    }

    private void setPhoneProps(Phone phone, ResultSet resultSet) throws SQLException {
        phone.setId(resultSet.getLong("id"));
        phone.setBrand(resultSet.getString("brand"));
        phone.setModel(resultSet.getString("model"));
        phone.setPrice(resultSet.getBigDecimal("price"));
        phone.setDisplaySizeInches(resultSet.getBigDecimal("displaySizeInches"));
        phone.setWeightGr(getIntObject(resultSet, "weightGr"));
        phone.setLengthMm(resultSet.getBigDecimal("lengthMm"));
        phone.setWidthMm(resultSet.getBigDecimal("widthMm"));
        phone.setHeightMm(resultSet.getBigDecimal("heightMm"));
        phone.setAnnounced(resultSet.getDate("announced"));
        phone.setDeviceType(resultSet.getString("deviceType"));
        phone.setOs(resultSet.getString("os"));
        phone.setDisplayResolution(resultSet.getString("displayResolution"));
        phone.setPixelDensity(getIntObject(resultSet, "pixelDensity"));
        phone.setDisplayTechnology(resultSet.getString("displayTechnology"));
        phone.setBackCameraMegapixels(resultSet.getBigDecimal("backCameraMegapixels"));
        phone.setFrontCameraMegapixels(resultSet.getBigDecimal("frontCameraMegapixels"));
        phone.setRamGb(resultSet.getBigDecimal("ramGb"));
        phone.setInternalStorageGb(resultSet.getBigDecimal("internalStorageGb"));
        phone.setBatteryCapacityMah(getIntObject(resultSet, "batteryCapacityMah"));
        phone.setTalkTimeHours(resultSet.getBigDecimal("talkTimeHours"));
        phone.setStandByTimeHours(resultSet.getBigDecimal("standByTimeHours"));
        phone.setBluetooth(resultSet.getString("bluetooth"));
        phone.setPositioning(resultSet.getString("positioning"));
        phone.setImageUrl(resultSet.getString("imageUrl"));
        phone.setDescription(resultSet.getString("description"));
    }

    private Integer getIntObject(ResultSet resultSet, String key) throws SQLException {
        Object intObject = resultSet.getObject(key);
        return (intObject == null) ? null : (int) (short) intObject;
    }
}
