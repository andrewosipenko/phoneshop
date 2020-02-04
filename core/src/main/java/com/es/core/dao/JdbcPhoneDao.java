package com.es.core.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String SELECT_PHONE_BY_ID = "select * from phones where id = ?";
    private static final String SELECT_PHONE2COLOR_BY_ID = "select * from phone2color where phoneId = ?";
    private static final String SELECT_PHONES_OFFSET_LIMIT = "select * from phones offset ? limit ?";
    private static final String COLOR_ID = "colorId";
    private static final String PHONES_TABLE_NAME ="phones";
    private static final String GENERATED_KEY_NAME = "id";
    private static final String DUPLICATE_ENTRY_MESSAGE = " Duplicate entry, such kind of item already exists";
    private static final String[] FIELD_NAMES = {"id", "brand", "model", "price", "displaySizeInches", "weightGr",
            "lengthMm", "widthMm", "heightMm", "announced", "deviceType", "os", "displayResolution", "pixelDensity",
            "displayTechnology", "backCameraMegapixels", "frontCameraMegapixels", "ramGb", "internalStorageGb",
            "batteryCapacityMah", "talkTimeHours", "standByTimeHours", "bluetooth", "positioning", "imageUrl",
            "description"};

    private final ColorDao colorDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPhoneDao(JdbcTemplate jdbcTemplate, ColorDao colorDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.colorDao = colorDao;
    }

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_BY_ID, new Object[]{key},
                new BeanPropertyRowMapper<>(Phone.class));
        if (phones != null && !phones.isEmpty()) {
            setPhoneColors(phones.get(0));
            return Optional.of(phones.get(0));
        }
        return Optional.empty();
    }

    public void save(final Phone phone) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(PHONES_TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_NAME);
        Map<String,Object> parameters = new HashMap<>();
        fillMapForSaving(parameters, FIELD_NAMES, phone);
        try {
            simpleJdbcInsert.execute(parameters);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(DUPLICATE_ENTRY_MESSAGE, e);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONES_OFFSET_LIMIT, new Object[]{offset, limit},
                new BeanPropertyRowMapper(Phone.class));
        for (Phone phone : phones) {
            setPhoneColors(phone);
        }
        return phones;
    }

    private void setPhoneColors(Phone phone) {
        List<Long> colorIds = jdbcTemplate.query(SELECT_PHONE2COLOR_BY_ID, new Object[]{phone.getId()},
                (resultSet, i) -> resultSet.getLong(COLOR_ID));
        if (colorIds != null) {
            setPhoneColorsByIds(phone, colorIds);
        }
    }

    private void setPhoneColorsByIds(Phone phone, List<Long> colorIds) {
        Set<Color> phoneColors = new HashSet<>();
        List<Color> allColors = colorDao.getAllColors();
        for (Long colorId : colorIds) {
            Optional<Color> colors = allColors.stream().filter(color -> color.getId().equals(colorId)).findFirst();
            colors.ifPresent(phoneColors::add);
        }
        phone.setColors(phoneColors);
    }

    protected void fillMapForSaving(Map<String,Object> map,String[] fieldNames, Phone phone){
        map.put(fieldNames[0], phone.getId());
        map.put(fieldNames[1], phone.getBrand());
        map.put(fieldNames[2], phone.getModel());
        map.put(fieldNames[3], phone.getPrice());
        map.put(fieldNames[4], phone.getDisplaySizeInches());
        map.put(fieldNames[5], phone.getWeightGr());
        map.put(fieldNames[6], phone.getLengthMm());
        map.put(fieldNames[7], phone.getWidthMm());
        map.put(fieldNames[8], phone.getHeightMm());
        map.put(fieldNames[9], phone.getAnnounced());
        map.put(fieldNames[10], phone.getDeviceType());
        map.put(fieldNames[11], phone.getOs());
        map.put(fieldNames[12], phone.getDisplayResolution());
        map.put(fieldNames[13], phone.getPixelDensity());
        map.put(fieldNames[14], phone.getDisplayTechnology());
        map.put(fieldNames[15], phone.getBackCameraMegapixels());
        map.put(fieldNames[16], phone.getFrontCameraMegapixels());
        map.put(fieldNames[17], phone.getRamGb());
        map.put(fieldNames[18], phone.getInternalStorageGb());
        map.put(fieldNames[19], phone.getBatteryCapacityMah());
        map.put(fieldNames[20], phone.getTalkTimeHours());
        map.put(fieldNames[21], phone.getStandByTimeHours());
        map.put(fieldNames[22], phone.getBluetooth());
        map.put(fieldNames[23], phone.getPositioning());
        map.put(fieldNames[24], phone.getImageUrl());
        map.put(fieldNames[25], phone.getDescription());
    }

}
