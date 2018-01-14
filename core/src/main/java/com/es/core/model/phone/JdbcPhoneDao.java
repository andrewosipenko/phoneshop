package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao{

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_INFO_BY_ID = "SELECT phones.id AS phoneId, brand, model, price, displaySizeInches, " +
                                                        "weightGr, lengthMm, widthMm, heightMm, announced, deviceType, " +
                                                        "os, displayResolution, pixelDensity, displayTechnology, " +
                                                        "backCameraMegapixels, frontCameraMegapixels, ramGb, " +
                                                        "internalStorageGb, batteryCapacityMah, talkTimeHours, " +
                                                        "standByTimeHours, bluetooth, positioning, imageUrl, " +
                                                        "description, colors.id AS colorId, code " +
                                                        "FROM phones " +
                                                        "JOIN phone2color ON phones.id = phone2color.phoneId " +
                                                        "JOIN colors ON colors.id = phone2color.colorId " +
                                                        "WHERE phones.id = ?";

    public Optional<Phone> get(final Long key) {
        List<Phone> phonesWithSameId = jdbcTemplate.query(SELECT_ALL_INFO_BY_ID, new Object[]{key}, new PhoneRowMapper());

        if (phonesWithSameId.size() == 0) {
            return Optional.empty();
        }

        Phone phone = phonesWithSameId.get(0);
        Set<Color> colors = new HashSet<>();

        for (Phone p : phonesWithSameId) {
            colors.add((Color) p.getColors().toArray()[0]);
        }

        phone.setColors(colors);

        return Optional.of(phone);
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }

    private static class PhoneRowMapper implements RowMapper<Phone> {

        @Override
        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            Phone phone = new Phone();

            phone.setId(rs.getLong("phoneId"));
            phone.setBrand(rs.getString("brand"));
            phone.setModel(rs.getString("model"));
            phone.setPrice(rs.getBigDecimal("price"));
            phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
            phone.setWeightGr(rs.getInt("weightGr"));
            phone.setLengthMm(rs.getBigDecimal("lengthMm"));
            phone.setWidthMm(rs.getBigDecimal("widthMm"));
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
            phone.setPositioning(rs.getString("positioning"));
            phone.setImageUrl(rs.getString("imageUrl"));
            phone.setDescription(rs.getString("description"));

            Set<Color> colors = new HashSet<>();
            Color color = new Color();

            color.setId(rs.getLong("colorId"));
            color.setCode(rs.getString("code"));
            colors.add(color);

            phone.setColors(colors);

            return phone;
        }

    }

}
