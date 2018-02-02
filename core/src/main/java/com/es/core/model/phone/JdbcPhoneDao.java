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
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {

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

    private static final String SELECT_ALL_IN_ORDER = "SELECT phones.id AS phoneId, brand, model, price, " +
                                                      "displaySizeInches, weightGr, lengthMm, widthMm, " +
                                                      "heightMm, announced, deviceType, os, " +
                                                      "displayResolution, pixelDensity, displayTechnology, " +
                                                      "backCameraMegapixels, frontCameraMegapixels, ramGb, " +
                                                      "internalStorageGb, batteryCapacityMah, talkTimeHours, " +
                                                      "standByTimeHours, bluetooth, positioning, imageUrl, " +
                                                      "description, colors.id AS colorId, code " +
                                                      "FROM phones " +
                                                      "JOIN phone2color ON phones.id = phone2color.phoneId " +
                                                      "JOIN colors ON colors.id = phone2color.colorId " +
                                                      "JOIN stocks ON phones.id = stocks.phoneId " +
                                                      "WHERE stock > 0 AND reserved > 0 " +
                                                      "ORDER BY ";

    private static final String SELECT_RECORDS_COUNT = "SELECT COUNT(*) FROM phones " +
                                                       "JOIN phone2color ON phones.id = phone2color.phoneId " +
                                                       "JOIN colors ON colors.id = phone2color.colorId " +
                                                       "JOIN stocks ON phones.id = stocks.phoneId " +
                                                       "WHERE stock > 0 AND reserved > 0";

    private static final String SELECT_RECORDS_COUNT_BY_MODEL = "SELECT COUNT(*) FROM phones " +
                                                                "JOIN phone2color ON phones.id = phone2color.phoneId " +
                                                                "JOIN colors ON colors.id = phone2color.colorId " +
                                                                "JOIN stocks ON phones.id = stocks.phoneId " +
                                                                "WHERE stock > 0 AND reserved > 0 AND model = ?";

    private static final String SELECT_BY_MODEL_IN_ORDER = "SELECT phones.id AS phoneId, brand, model, price, " +
                                                           "displaySizeInches, weightGr, lengthMm, widthMm, " +
                                                           "heightMm, announced, deviceType, os, " +
                                                           "displayResolution, pixelDensity, displayTechnology, " +
                                                           "backCameraMegapixels, frontCameraMegapixels, ramGb, " +
                                                           "internalStorageGb, batteryCapacityMah, talkTimeHours, " +
                                                           "standByTimeHours, bluetooth, positioning, imageUrl, " +
                                                           "description, colors.id AS colorId, code " +
                                                           "FROM phones " +
                                                           "JOIN phone2color ON phones.id = phone2color.phoneId " +
                                                           "JOIN colors ON colors.id = phone2color.colorId " +
                                                           "JOIN stocks ON phones.id = stocks.phoneId " +
                                                           "WHERE stock > 0 AND reserved > 0 AND model = ? " +
                                                           "ORDER BY ";

    @Override
    public Optional<Phone> get(final Long key) {
        List<Phone> phonesWithSameId = jdbcTemplate.query(SELECT_ALL_INFO_BY_ID, new Object[]{key}, new PhoneRowMapper());

        if (phonesWithSameId.isEmpty()) {
            return Optional.empty();
        }

        Phone phone = phonesWithSameId.get(0);

        Set<Color> colors = phonesWithSameId.stream()
                .map(onePhone -> (Color) onePhone.getColors().toArray()[0])
                .collect(Collectors.toSet());

        phone.setColors(colors);

        return Optional.of(phone);
    }

    @Override
    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }

    @Override
    public List<Phone> findAllInOrder(String orderBy, int limit, int offset) {
        String sql = SELECT_ALL_IN_ORDER + orderBy + " OFFSET ? LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{offset, limit}, new PhoneRowMapper());
    }

    @Override
    public List<Phone> findByModelInOrder(String model, String orderBy, int limit, int offset) {
        String sql = SELECT_BY_MODEL_IN_ORDER + orderBy + " OFFSET ? LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{model, offset, limit}, new PhoneRowMapper());
    }

    @Override
    public long productsCount() {
        return jdbcTemplate.queryForObject(SELECT_RECORDS_COUNT, Long.class);
    }

    @Override
    public long productsCountWithModel(String model) {
        return jdbcTemplate.queryForObject(SELECT_RECORDS_COUNT_BY_MODEL, Long.class, model);
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
