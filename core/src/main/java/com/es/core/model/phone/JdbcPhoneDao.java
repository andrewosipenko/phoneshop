package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    private static final String SELECT_RECORDS_COUNT_BY_MODEL = "SELECT COUNT(*) FROM phones " +
                                                                "JOIN stocks ON phones.id = stocks.phoneId " +
                                                                "WHERE phones.id in (SELECT phoneId FROM phone2color) AND " +
                                                                "stock > 0 AND price > 0 AND LOWER(model) LIKE LOWER(?)";

    private static final String SELECT_BY_MODEL_IN_ORDER = "SELECT p.id AS phoneId, brand, model, price, " +
                                                           "displaySizeInches, weightGr, lengthMm, widthMm, " +
                                                           "heightMm, announced, deviceType, os, " +
                                                           "displayResolution, pixelDensity, displayTechnology, " +
                                                           "backCameraMegapixels, frontCameraMegapixels, ramGb, " +
                                                           "internalStorageGb, batteryCapacityMah, talkTimeHours, " +
                                                           "standByTimeHours, bluetooth, positioning, imageUrl, " +
                                                           "description, colors.id AS colorId, code FROM " +
                                                           "(SELECT * FROM phones JOIN stocks ON phones.id = stocks.phoneId " +
                                                           "WHERE phones.id in (SELECT phoneId FROM phone2color) AND " +
                                                           "stock > 0 AND price > 0 AND LOWER(model) LIKE LOWER(?) " +
                                                           "LIMIT ? OFFSET ?) AS p " +
                                                           "JOIN phone2color ON p.id = phone2color.phoneId " +
                                                           "JOIN colors ON colors.id = phone2color.colorId " +
                                                           "ORDER BY %s %s";

    private static final String SELECT_COUNT_PRODUCT_IN_STOCK = "SELECT stock FROM stocks WHERE phoneId = ?";

    @Override
    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_ALL_INFO_BY_ID, new Object[]{key}, new PhoneExtractor());
        if (phones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phones.get(0));
    }

    @Override
    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Phone> findByModelInOrder(String model, String orderBy, int offset, int limit) {
        String column = orderBy.split("_")[0];
        String order = orderBy.split("_")[1];
        String sql = String.format(SELECT_BY_MODEL_IN_ORDER, column, order);
        return jdbcTemplate.query(sql, new Object[]{model, limit, offset}, new PhoneExtractor());
    }

    @Override
    public long productsCountWithModel(String model) {
        return jdbcTemplate.queryForObject(SELECT_RECORDS_COUNT_BY_MODEL, new Object[]{model}, Long.class);
    }

    @Override
    public long countProductInStock(Long id) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_PRODUCT_IN_STOCK, new Object[]{id}, Long.class);
    }

    private class PhoneExtractor implements ResultSetExtractor<List<Phone>> {
        @Override
        public List<Phone> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Phone> phones = new HashMap<>();
            List<Phone> phoneList = new LinkedList<>();
            while (rs.next()) {
                Phone phone;
                long phoneId = rs.getLong("phoneId");
                if (!phones.containsKey(phoneId)) {
                    phone = getPhoneWithProperties(rs);
                    phones.put(phoneId, phone);
                    phoneList.add(phone);
                } else {
                    phone = phones.get(phoneId);
                }
                addColor(phone, rs);
            }
            return phoneList;
        }

        private Phone getPhoneWithProperties(ResultSet rs) throws SQLException {
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
            phone.setColors(new HashSet<>());
            return phone;
        }

        private void addColor(Phone phone, ResultSet rs) throws SQLException {
            Set<Color> colors = phone.getColors();
            Color color = new Color();
            color.setId(rs.getLong("colorId"));
            color.setCode(rs.getString("code"));
            colors.add(color);
            phone.setColors(colors);
        }
    }
}
