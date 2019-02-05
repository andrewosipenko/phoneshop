package com.es.core.dao.phone;

import com.es.core.model.color.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcPhoneDao implements PhoneDao {
    private final String SQL_SELECT_ALL_PHONES = "select * from phones ";
    private final String SQL_SELECT_PHONE_BY_ID = SQL_SELECT_ALL_PHONES + "where id = :id";
    private final String SQL_INSERT_INTO_PHONES = "insert into phones values (" +
            ":id, :brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm, :announced," +
            " :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, :backCameraMegapixels, :frontCameraMegapixels," +
            " :ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    private final String SQL_INSERT_INTO_PHONE2COLOR = "insert into phone2color values (:phoneId, :colorId)";
    private final String SQL_SELECT_MAX_ID_FROM_PHONES = "select max(id) from phones";
    private final String SQL_LIMIT_OFFSET = " limit ? offset ?";
    private final String SQL_SEARCH = "and lower(brand) like ? ";
    private final String SQL_AVAILABLE_PHONES = "inner join stocks on phones.id = stocks.phoneId where stocks.stock > 0 and phones.price is not null ";
    private final String SQL_SELECT_ALL_COLORS = "select * from colors";
    private final String SQL_DELETE_FROM_PHONES_BY_ID = "delete from phones where id = :id";
    private final String SQL_COUNT_PHONES = "select count(*) from phones ";
    private final String SQL_SEARCH_AND_SORT = SQL_SEARCH + "order by phones.";
    private final String SQL_SELECT_COLORS_BELONGS_TO_PHONE_ID = SQL_SELECT_ALL_COLORS + " inner join phone2color " +
            "on colors.id = phone2color.colorId where phone2color.phoneId = ";
    private final String SQL_NUMBER_AVAILABLE_PHONES = SQL_COUNT_PHONES + SQL_AVAILABLE_PHONES + SQL_SEARCH;
    private final String SQL_COUNT_PHONES_WITH_ID = SQL_COUNT_PHONES + "where phones.id = :id";
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SqlParameterSource sqlParameterSource;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Color.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Phone> get(Long key) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("id", key);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            Phone phone = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_PHONE_BY_ID, sqlParameterSource, phoneBeanPropertyRowMapper);
            setColor(phone);
            return Optional.of(phone);
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public void save(Phone phone) {
        if (phone == null) {
            throw new IllegalArgumentException();
        }
        if (phone.getId() == null) {
            phone.setId(getMaxPhoneId() + 1);
        }

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_INSERT_INTO_PHONES, getAllValuesPhone(phone));

        for (Color color : phone.getColors()) {
            this.namedParameterJdbcTemplate.update(SQL_INSERT_INTO_PHONE2COLOR, getAllValuesPhone2Color(phone.getId(), color.getId()));
        }
    }

    @Override
    public long getMaxPhoneId() {
        return this.jdbcTemplate.queryForObject(SQL_SELECT_MAX_ID_FROM_PHONES, long.class);
    }

    @Override
    public long getCountPhones() {
        return this.jdbcTemplate.queryForObject(SQL_COUNT_PHONES, long.class);
    }

    private void setColor(Phone phone) {
        Long phoneId = phone.getId();
        List<Color> colors = jdbcTemplate.query(SQL_SELECT_COLORS_BELONGS_TO_PHONE_ID + phoneId, colorBeanPropertyRowMapper);
        phone.setColors(new HashSet<>(colors));
    }

    @Override
    public void delete(Phone phone) {
        this.sqlParameterSource = new MapSqlParameterSource("id", phone.getId());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_DELETE_FROM_PHONES_BY_ID, sqlParameterSource);
    }

    @Override
    public int getNumberAvailablePhones(String search) {
        search = "%".concat(search.trim().toLowerCase()).concat("%");
        return this.jdbcTemplate.queryForObject(SQL_NUMBER_AVAILABLE_PHONES, Integer.class, search);
    }

    @Override
    public List<Phone> findAll(int offset, int limit, String search, String sort, String direction) {
        search = ("%").concat(search.trim().toLowerCase()).concat("%");
        List<Phone> phones = this.jdbcTemplate.query(SQL_SELECT_ALL_PHONES + SQL_AVAILABLE_PHONES + SQL_SEARCH_AND_SORT +
                sort  + " " + direction.toUpperCase() + SQL_LIMIT_OFFSET, phoneBeanPropertyRowMapper, search, limit, offset);
        for (Phone phone : phones) {
            setColor(phone);
        }
        return phones;
    }

    @Override
    public boolean contains(Long key){
        this.sqlParameterSource = new MapSqlParameterSource("id", key);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return this.namedParameterJdbcTemplate.queryForObject(SQL_COUNT_PHONES_WITH_ID, sqlParameterSource, Long.class) > 0;
    }

    private Map<String, Object> getAllValuesPhone2Color(long phoneId, long colorId) {
        Map<String, Object> allValuesPhone2Color = new HashMap<>();
        allValuesPhone2Color.put("phoneId", phoneId);
        allValuesPhone2Color.put("colorId", colorId);
        return allValuesPhone2Color;
    }

    private Map<String, Object> getAllValuesPhone(Phone phone) {
        Map<String, Object> allValuesPhone = new HashMap<>();
        allValuesPhone.put("id", phone.getId());
        allValuesPhone.put("brand", phone.getBrand());
        allValuesPhone.put("model", phone.getModel());
        allValuesPhone.put("price", phone.getPrice());
        allValuesPhone.put("displaySizeInches", phone.getDisplaySizeInches());
        allValuesPhone.put("weightGr", phone.getWeightGr());
        allValuesPhone.put("lengthMm", phone.getLengthMm());
        allValuesPhone.put("widthMm", phone.getWidthMm());
        allValuesPhone.put("heightMm", phone.getHeightMm());
        allValuesPhone.put("announced", phone.getAnnounced());
        allValuesPhone.put("deviceType", phone.getDeviceType());
        allValuesPhone.put("os", phone.getOs());
        allValuesPhone.put("displayResolution", phone.getDisplayResolution());
        allValuesPhone.put("pixelDensity", phone.getPixelDensity());
        allValuesPhone.put("displayTechnology", phone.getDisplayTechnology());
        allValuesPhone.put("backCameraMegapixels", phone.getBackCameraMegapixels());
        allValuesPhone.put("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        allValuesPhone.put("ramGb", phone.getRamGb());
        allValuesPhone.put("internalStorageGb", phone.getInternalStorageGb());
        allValuesPhone.put("batteryCapacityMah", phone.getBatteryCapacityMah());
        allValuesPhone.put("talkTimeHours", phone.getTalkTimeHours());
        allValuesPhone.put("standByTimeHours", phone.getStandByTimeHours());
        allValuesPhone.put("bluetooth", phone.getBluetooth());
        allValuesPhone.put("positioning", phone.getPositioning());
        allValuesPhone.put("imageUrl", phone.getImageUrl());
        allValuesPhone.put("description", phone.getDescription());
        return allValuesPhone;
    }
}
