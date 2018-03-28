package com.es.core.model.phone;

import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PhoneListResultSetExtractor implements ResultSetExtractor<List<Phone>> {

    public PhoneListResultSetExtractor() {
    }

    @Override
    public List<Phone> extractData(ResultSet rs) throws SQLException {

        Map<Long, Phone> phoneMap = new HashMap<>();
        List<Phone> phoneList = new ArrayList<>();

        while (rs.next()) {
            Long phoneId = rs.getLong("phoneId");
            Phone changePhone = phoneMap.get(phoneId);
            if (changePhone == null) {
                changePhone = getProperties(rs);
                phoneMap.put(phoneId, changePhone);
                phoneList.add(changePhone);
            }
            addColor(changePhone, rs);
        }

        return phoneList;
    }

    private Phone getProperties(ResultSet rs) throws SQLException {
        Phone phone = new Phone();

        phone.setId(rs.getLong("phoneId"));
        phone.setBrand(rs.getString("brand"));
        phone.setModel(rs.getString("model"));
        phone.setPrice(rs.getBigDecimal("price"));
        phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
        phone.setWeightGr(rs.getInt("weightGr"));
        phone.setLengthMm(rs.getBigDecimal("lengthMm"));
        phone.setWidthMm(rs.getBigDecimal("widthMm"));
        phone.setHeightMm(rs.getBigDecimal("heightMm"));
        phone.setAnnounced(rs.getDate("announced"));
        phone.setDeviceType(rs.getString("deviceType"));
        phone.setOs(rs.getString("os"));
        phone.setDisplayResolution(rs.getString("displayResolution"));
        phone.setPixelDensity(rs.getInt("pixelDensity"));
        phone.setDisplayTechnology(rs.getString("displayTechnology"));
        phone.setBackCameraMegapixels(rs.getBigDecimal("backCameraMegapixels"));
        phone.setFrontCameraMegapixels(rs.getBigDecimal("frontCameraMegapixels"));
        phone.setRamGb(rs.getBigDecimal("ramGb"));
        phone.setInternalStorageGb(rs.getBigDecimal("internalStorageGb"));
        phone.setBatteryCapacityMah(rs.getInt("batteryCapacityMah"));
        phone.setTalkTimeHours(rs.getBigDecimal("talkTimeHours"));
        phone.setStandByTimeHours(rs.getBigDecimal("standByTimeHours"));
        phone.setBluetooth(rs.getString("bluetooth"));
        phone.setImageUrl(rs.getString("imageUrl"));
        phone.setDescription(rs.getString("description"));
        phone.setColors(new HashSet<>());
        return phone;
    }

    private void addColor(Phone phone, ResultSet rs) throws SQLException {
        Long colorId = rs.getLong("colorId");
        if (colorId > 0) {
            Color newColor = new Color();
            newColor.setCode(rs.getString("code"));
            newColor.setId(colorId);
            phone.getColors().add(newColor);
        }
    }
}
