package com.es.core.dao.phone;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.OrderBy;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.PreparedStatement;
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
            "left join phone2color on phones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId " +
            "where phones.id = ?";

    private final static String SELECT_PHONE_LIST_QUERY = "select phones.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from phones " +
            "left join phone2color on phones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId " +
            "where phones.id IN (:phoneIdList)";

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
            "(select * from phones where price > 0 and (lower(brand) like ? or lower(model) like ?) order by ";

    private final static String SECOND_PART_OF_SEARCH_PHONES_QUERY = SECOND_PART_OF_SELECT_ORDERED_PHONE_QUERY;

    private final static String QUERY_OF_PHONE_COUNT_BY_QUERY = "select COUNT(1) from phones where price > 0 and " +
            "(lower(brand) like ? or lower(model) like ?)";

    private final static String SELECT_STOCK_LIST_QUERY = "select * from stocks where phoneId IN (:phoneIdList)";

    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertPhone;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    public void init() {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.insertPhone = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_QUERY, PhoneListResultSetExtractor.getInstanse(), key);
        if (phones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phones.get(0));
    }

    @Override
    public List<Phone> getPhonesByIdList(List<Long> idList) {
        Map<String, Object> namedParameters = Collections.singletonMap("phoneIdList", idList);
        return namedParameterJdbcTemplate.query(SELECT_PHONE_LIST_QUERY, namedParameters, PhoneListResultSetExtractor.getInstanse());
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
        return jdbcTemplate.query(
                FIRST_PART_OF_SELECT_ORDERED_PHONE_QUERY + orderBy.getSqlCommand() + SECOND_PART_OF_SELECT_ORDERED_PHONE_QUERY,
                PhoneListResultSetExtractor.getInstanse(), offset, limit);
    }

    @Override
    public int phoneCount() {
        return jdbcTemplate.queryForObject(PHONES_COUNT_QUERY, Integer.class);
    }

    @Override
    public List<Phone> getPhonesByQuery(String query, OrderBy orderBy, int offset, int limit) {
        String formattedQuery = "%" + query.toLowerCase() + "%";
        return jdbcTemplate.query(
                FIRST_PART_OF_SEARCH_PHONES_QUERY + orderBy.getSqlCommand() + SECOND_PART_OF_SEARCH_PHONES_QUERY,
                PhoneListResultSetExtractor.getInstanse(), formattedQuery, formattedQuery, offset, limit);
    }

    @Override
    public int phoneCountByQuery(String query) {
        String formattedQuery = "%" + query.toLowerCase() + "%";
        return jdbcTemplate.queryForObject(QUERY_OF_PHONE_COUNT_BY_QUERY, Integer.class, formattedQuery, formattedQuery);
    }

    @Override
    public List<Stock> getStocks(List<Long> phoneIdList) {
        Map namedParameters = Collections.singletonMap("phoneIdList", phoneIdList);
        return namedParameterJdbcTemplate.query(SELECT_STOCK_LIST_QUERY, namedParameters, new BeanPropertyRowMapper<Stock>(Stock.class));
    }

}
