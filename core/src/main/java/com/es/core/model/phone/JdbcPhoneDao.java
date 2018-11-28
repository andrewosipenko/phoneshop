package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private final String SQL_SELECT_ALL_PHONES_BY_ID = "select * from phones where id = :id";
    private final String SQL_INSERT_INTO_PHONES = "insert into phones values (" +
            ":id, :brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm, :announced," +
            " :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, :backCameraMegapixels, :frontCameraMegapixels," +
            " :ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    private final String SQL_INSERT_INTO_PHONE2COLOR = "insert into phone2color values (:phoneId, :colorId)";
    private final String SQL_SELECT_MAX_ID_FROM_PHONES = "select max(id) from phones";
    private final String SQL_SELECT_ALL_FROM_PHONES_OFFSET_LIMIT = "select * from phones limit ? offset ?";
    private final String SQL_SELECT_ALL_FROM_COLORS = "select * from colors";
    private final String SQL_SELECT_COLORID_FROME_PHONE2COLOR_BY_PHONEID = "select colorId from phone2color where phoneId = :phoneId";
    private final String SQL_DELETE_FROM_PHONES_BY_ID = "delete from phones where id = :id";

    @Resource
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    SqlParameterSource sqlParameterSource;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Color.class);

    public Optional<Phone> get(Long key) {
        this.sqlParameterSource = new MapSqlParameterSource("id", key);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Phone phone = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_ALL_PHONES_BY_ID, sqlParameterSource, phoneBeanPropertyRowMapper);
        return Optional.ofNullable(phone);
    }

    public void save(Phone phone) {
        if (phone == null || get(phone.getId()).isPresent()) {
            throw new IllegalArgumentException();
        }
        if (phone.getId() == null) {
            phone.setId(maxPhoneId() + 1);
        }

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_INSERT_INTO_PHONES, getAllValuesPhone(phone));

        for (Color color : phone.getColors()) {
            this.namedParameterJdbcTemplate.update(SQL_INSERT_INTO_PHONE2COLOR, getAllValuesPhone2Color(phone.getId(), color.getId()));
        }
    }

    private long maxPhoneId() {
        return this.jdbcTemplate.queryForObject(SQL_SELECT_MAX_ID_FROM_PHONES, long.class);
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = this.jdbcTemplate.query(SQL_SELECT_ALL_FROM_PHONES_OFFSET_LIMIT, new Object[]{limit, offset}, phoneBeanPropertyRowMapper);
        List<Color> colors = this.jdbcTemplate.query(SQL_SELECT_ALL_FROM_COLORS, colorBeanPropertyRowMapper);
        putColorsInPhones(phones, colors);
        return phones;
    }

    private void putColorsInPhones(List<Phone> phones, List<Color> colors) {
        Map<Long, String> colorsMap = colors.stream().collect(Collectors.toMap(Color::getId, Color::getCode));
        for (Phone phone : phones) {
            this.sqlParameterSource = new MapSqlParameterSource("phoneId", phone.getId());
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            List<Long> colorId = this.namedParameterJdbcTemplate.queryForList(SQL_SELECT_COLORID_FROME_PHONE2COLOR_BY_PHONEID, sqlParameterSource, long.class);
            Set<Color> colorsForPhone = new HashSet();
            for (Long id : colorId) {
                colorsForPhone.add(new Color(id, colorsMap.get(id)));
            }
            phone.setColors(colorsForPhone);
        }
    }

    public void delete(Phone phone) {
        this.sqlParameterSource = new MapSqlParameterSource("id", phone.getId());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_DELETE_FROM_PHONES_BY_ID, sqlParameterSource);
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
