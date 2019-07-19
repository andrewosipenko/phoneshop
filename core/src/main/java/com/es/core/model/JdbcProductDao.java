package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcProductDao implements ProductDao {

    private static final String SELECT_BY_ID = "SELECT phones.id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description, colors.id, colors.code FROM phones JOIN phone2color ON phones.id=phone2color.phoneId JOIN colors ON phone2color.colorId=colors.id WHERE phones.id=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Phone> load(Long key) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{key}, new ProductRowMapper()));
    }

    private static final class ProductRowMapper implements RowMapper<Phone> {
        @Override
        public Phone mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Set<Color> colors = new HashSet<>();
            Phone phone = new Phone();
            phone.setId(resultSet.getLong("id"));
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
            for (int i = 27; i < resultSet.getMetaData().getColumnCount(); i++) {
                Color color = new Color();
                color.setId(resultSet.getLong(i++));
                color.setCode(resultSet.getString(i));
                colors.add(color);
            }
            phone.setColors(colors);
            return phone;
        }
    }
}
