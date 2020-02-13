package com.es.core.dao.impl;

import com.es.core.configurer.PhoneListResultSetExtractor;
import com.es.core.configurer.PhoneParametersPreparer;
import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private final static String SELECT_PHONE_BY_ID_QUERY = "select phones.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from phones " +
            "left join phone2color on phones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId " +
            "where phones.id = ?";

    private final static String SELECT_ALL_PHONES_QUERY = "select limited.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from " +
            "(select * from phones where id in (select phoneId from stocks where stock > 0) offset ? limit ? ) as limited " +
            "left join phone2color on limited.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId ";

    private final static String UPDATE_PHONE = "update phones set brand = ? ,model = ? ," +
            "price = ? ,displaySizeInches = ? ,weightGr = ? ,lengthMm = ? ,widthMm = ? ," +
            "heightMm = ? ,announced = ? ,deviceType = ? ,os = ? ,displayResolution = ? ," +
            "pixelDensity = ? ,displayTechnology = ? ,backCameraMegapixels = ? ," +
            "frontCameraMegapixels = ? ,ramGb = ? ,internalStorageGb = ? ,batteryCapacityMah = ? ," +
            "talkTimeHours = ? ,standByTimeHours = ? ,bluetooth = ? ,positioning = ? ,imageUrl = ? ," +
            "description = ? where id = ? ";

    private final static String QUERY_OF_PHONE_COUNT_BY_QUERY = "select COUNT(1) from phones where price > 0 and " +
            "(lower(brand) like ? or lower(model) like ?)";

    private final static String DELETE_COLORS_IN_PHONE2COLOR = "delete from phone2color where phoneId = ?";
    private static final String INSERT_INTO_PHONE2COLOR = "insert into phone2color values (?, ?)";
    private static final String PHONES_TABLE_NAME = "phones";
    private static final String GENERATED_KEY_NAME = "id";
    private static final String DUPLICATE_ENTRY_MESSAGE = " Duplicate entry, such kind of item already exists";
    private static final String PART_1_SELECT_PHONES_BY_SEARCH_QUERY_ORDERED = "select limitedPhones.id AS phoneId, " +
            "brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from " +
            "(select * from phones where price > 0 and (lower(brand) like ? or lower(model) like ?) order by ";
    private static final String PART_2_SELECT_PHONES_BY_SEARCH_QUERY_ORDERED_OFFSET = " offset ? limit ? ) as limitedPhones " +
            "left join phone2color on limitedPhones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId";

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private PhoneParametersPreparer parametersPreparer;

    @Autowired
    public JdbcPhoneDao(JdbcTemplate jdbcTemplate, PhoneParametersPreparer phoneParametersPreparer) {
        this.jdbcTemplate = jdbcTemplate;
        this.parametersPreparer = phoneParametersPreparer;
    }

    @PostConstruct
    void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(PHONES_TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_NAME);
    }

    public Optional<Phone> get(Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_BY_ID_QUERY, new PhoneListResultSetExtractor(), key);
        if (phones != null && !phones.isEmpty()) {
            return Optional.of(phones.get(0));
        }
        return Optional.empty();
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(SELECT_ALL_PHONES_QUERY, new PhoneListResultSetExtractor(), offset, limit);
    }

    @Override
    public int countPhonesByQuery(String searchQuery) {
        String preparedSearchQuery = '%' + searchQuery.toLowerCase() + '%';
        return jdbcTemplate.queryForObject(QUERY_OF_PHONE_COUNT_BY_QUERY, Integer.class, preparedSearchQuery,
                preparedSearchQuery);
    }

    @Override
    public List<Phone> getPhonesByQuery(String searchQuery, String sort, String order, int offset, int limit) {
        String preparedSearchQuery = '%' + searchQuery.toLowerCase() + '%';
        String preparedOrder = sort + ' ' + order;
        return jdbcTemplate.query(PART_1_SELECT_PHONES_BY_SEARCH_QUERY_ORDERED + preparedOrder
                        + PART_2_SELECT_PHONES_BY_SEARCH_QUERY_ORDERED_OFFSET, new PhoneListResultSetExtractor(),
                preparedSearchQuery, preparedSearchQuery, offset, limit);
    }

    public void save(Phone phone) {
        if (phone.getId() == null) {
            savePhone(phone);
        } else {
            updatePhone(phone);
        }
    }

    private void updatePhone(Phone phone) {
        jdbcTemplate.update(UPDATE_PHONE, parametersPreparer.getPreparedParameters(phone));
        jdbcTemplate.update(DELETE_COLORS_IN_PHONE2COLOR, phone.getId());
        saveColor(phone);
    }

    private void savePhone(Phone phone) {
        Map<String, Object> parameters = parametersPreparer.fillMapForSaving(phone);
        try {
            Long phoneId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
            phone.setId(phoneId);
            saveColor(phone);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(DUPLICATE_ENTRY_MESSAGE, e);
        }
    }

    private void saveColor(Phone phone) {
        jdbcTemplate.batchUpdate(INSERT_INTO_PHONE2COLOR, new BatchPreparedStatementSetter() {
            List<Color> colors = new ArrayList<>(phone.getColors());

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, phone.getId());
                preparedStatement.setLong(2, colors.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return colors.size();
            }
        });
    }
}
