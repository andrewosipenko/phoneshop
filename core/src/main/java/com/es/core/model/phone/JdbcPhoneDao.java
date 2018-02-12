package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {

    private final static String SELECT_PHONE_QUERY = "select phones.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from phones " +
            "join phone2color on phones.id = phone2color.phoneId " +
            "join colors on colors.id = phone2color.colorId " +
            "where phones.id = ?";

    private final static String UPDATE_PHONE_QUERY = "update phones set brand = ? ,model = ? ," +
            "price = ? ,displaySizeInches = ? ,weightGr = ? ,lengthMm = ? ,widthMm = ? ," +
            "heightMm = ? ,announced = ? ,deviceType = ? ,os = ? ,displayResolution = ? ," +
            "pixelDensity = ? ,displayTechnology = ? ,backCameraMegapixels = ? ," +
            "frontCameraMegapixels = ? ,ramGb = ? ,internalStorageGb = ? ,batteryCapacityMah = ? ," +
            "talkTimeHours = ? ,standByTimeHours = ? ,bluetooth = ? ,positioning = ? ,imageUrl = ? ," +
            "description = ? where id = ? ";

    private final static String DELETE_COLORS_QUERY = "delete from phone2color where phoneId = ?";

    private final static String INSERT_COLORS_QUERY = "insert into phone2color (phoneId,colorId) values (?,?)";

    private final static String FIRST_PART_OF_SELECT_ORDERED_PHONE_QUERY = "select limitedPhones.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from " +
            "(select * from phones where price > 0 order by ";

    private final static String SECOND_PART_OF_SELECT_ORDERED_PHONE_QUERY = " offset ? limit ? ) as limitedPhones " +
            "left join phone2color on limitedPhones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId";

    private final static String PHONES_COUNT_QUERY = "select COUNT(1) from phones where price > 0";

    private final static String FIRST_PART_OF_SEARCH_PHONES_QUERY = "select limitedPhones.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from " +
            "(select * from phones where price > 0 and (brand like ? or model like ?) order by ";

    private final static String SECOND_PART_OF_SEARCH_PHONES_QUERY = SECOND_PART_OF_SELECT_ORDERED_PHONE_QUERY;

    private final static String QUERY_OF_PHONE_COUNT_BY_QUERY = "select COUNT(1) from phones where price > 0 and " +
            "(brand like ? or model like ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertPhone;

    @PostConstruct
    private void initSimpleJdbcInsert() {
        this.insertPhone = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_QUERY, new PhoneListResultSetExtractor(), key);
        if (phones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phones.get(0));
    }

    public void save(final Phone phone) {
        if (phone.getId() == null) {
            insert(phone);
        } else {
            update(phone);
        }
    }

    private void insert(final Phone phone) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(phone);
        final long newId = insertPhone.executeAndReturnKey(parameters).longValue();
        phone.setId(newId);
        insertColors(phone);
    }

    private void update(final Phone phone) {
        jdbcTemplate.update(UPDATE_PHONE_QUERY,
                phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription(), phone.getId());

        jdbcTemplate.update(DELETE_COLORS_QUERY, phone.getId());
        insertColors(phone);
    }

    private void insertColors(final Phone phone) {
        jdbcTemplate.batchUpdate(INSERT_COLORS_QUERY,
                new BatchPreparedStatementSetter() {

                    List<Color> colors = new ArrayList<>(phone.getColors());

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, phone.getId());
                        ps.setLong(2, colors.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return colors.size();
                    }
                });
    }

    public List<Phone> findAll(int offset, int limit) {
        return findAllInOrder(OrderBy.BRAND, offset, limit);
    }

    @Override
    public List<Phone> findAllInOrder(OrderBy orderBy, int offset, int limit) {
        return jdbcTemplate.query(FIRST_PART_OF_SELECT_ORDERED_PHONE_QUERY + orderBy.getSqlCommand() + SECOND_PART_OF_SELECT_ORDERED_PHONE_QUERY, new PhoneListResultSetExtractor(),
                offset, limit);
    }

    @Override
    public int phonesCount() {
        return jdbcTemplate.queryForObject(PHONES_COUNT_QUERY,Integer.class);
    }

    @Override
    public List<Phone> getPhonesByQuery(String query, OrderBy orderBy, int offset, int limit) {
        query = "%"+query+"%";
        return jdbcTemplate.query(FIRST_PART_OF_SEARCH_PHONES_QUERY + orderBy.getSqlCommand() + SECOND_PART_OF_SEARCH_PHONES_QUERY, new PhoneListResultSetExtractor(),
                query, query, offset, limit);
    }

    @Override
    public int phonesCountByQuery(String query) {
        query = "%"+query+"%";
        return jdbcTemplate.queryForObject(QUERY_OF_PHONE_COUNT_BY_QUERY,Integer.class,query,query);
    }

    private class PhoneListResultSetExtractor implements ResultSetExtractor<List<Phone>> {
        @Override
        public List<Phone> extractData(ResultSet rs) throws SQLException {

            Map<Long, Phone> phoneMap = new HashMap<>();
            List<Phone> phoneList = new ArrayList<>();

            while (rs.next()) {
                Phone changePhone;
                Long phoneId = rs.getLong("phoneId");
                if (!phoneMap.containsKey(phoneId)) {
                    changePhone = readPropertiesToPhone(rs);
                    phoneMap.put(phoneId, changePhone);
                    phoneList.add(changePhone);
                } else {
                    changePhone = phoneMap.get(phoneId);
                }
                addColor(changePhone, rs);
            }

            return phoneList;
        }

        private Phone readPropertiesToPhone(ResultSet rs) throws SQLException {
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
            Color newColor = new Color();
            newColor.setCode(rs.getString("colorCode"));
            newColor.setId(rs.getLong("colorId"));
            phone.getColors().add(newColor);
        }
    }
}
