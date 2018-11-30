package com.es.core.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcProductDao implements PhoneDao {
    private static final String SQL_QUERY_FOR_GETTING_ALL_COLORS = "select * from colors";
    private static final String SQL_FOR_GETTING_PHONE_BY_ID = "select * from phones where phones.id=?";
    private static final String SQL_FOR_GETTING_LAST_PHONE_ID = "select MAX(id) as lastId from phones";
    private static final String SQL_FOR_BINDING_PHONE_AND_COLOR = "insert into phone2color values (?,?)";
    private static final String SQL_FOR_GETTING_AVAILABLE_PHONES_BY_OFFSET_AND_LIMIT = "select * from phones join stocks on phones.id=stocks.phoneId where (stocks.stock - stocks.reserved > 0) and phones.price is not null offset ? limit ?";
    private static final String SQL_FOR_DELETING_PHONE_BY_ID = "delete from phones where id = ?";
    private static final String SQL_FOR_GETTING_COLORS_BY_PHONE_ID = "select * from phone2color where phone2color.phoneId = ?";
    private static final String SQL_FOR_GETTING_STOCK_BY_PHONE_ID = "select * from stocks where stocks.phoneId = ?";
    private static final String SQL_FOR_GETTING_TOTAL_AMOUNT_OF_AVAILABLE = "select count(*) from phones join stocks on phones.id=stocks.phoneId where (stocks.stock - stocks.reserved > 0) and phones.price is not null";
    private static final String SQL_FOR_CHANGE_RESERVED_QUANTITY = "update stocks set reserved = reserved + ? where phoneId = ?";
    private static final String SQL_FOR_GETTING_PHONES_BY_KEYWORD = "select * from phones where brand=? or model=?";
    @Resource
    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);

    public void save(Phone phone) {
        checkPhoneIdAndSetIfNeeded(phone);
        insertPhone(phone);
        bindPhoneAndColor(phone);
    }

    private void checkPhoneIdAndSetIfNeeded(Phone phone) {
        if (phone.getId() == null) {
            jdbcTemplate.query(SQL_FOR_GETTING_LAST_PHONE_ID,
                    (ResultSet resultSet) -> phone.setId(resultSet.getLong("lastId") + 1));
        } else {
            delete(phone.getId());
        }
    }

    private void delete(Long key) {
        jdbcTemplate.update(SQL_FOR_DELETING_PHONE_BY_ID, key);
    }

    private void insertPhone(Phone phone) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("phones");
        simpleJdbcInsert.execute(getPhoneValues(phone));
    }

    private void bindPhoneAndColor(Phone phone) {
        if (!phone.getColors().equals(Collections.EMPTY_SET)) {
            for (Color color : phone.getColors()) {
                jdbcTemplate.update(SQL_FOR_BINDING_PHONE_AND_COLOR, phone.getId(), color.getId());
            }
        }
    }

    public Optional<Phone> get(Long key) {
        Map<Long, Color> colors = getColors();
        Optional<Phone> phone = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FOR_GETTING_PHONE_BY_ID,
                (resultSet, i) -> phoneBeanPropertyRowMapper.mapRow(resultSet, 0), key));
        phone.ifPresent((p) -> setColorsForPhone(p, colors));
        return phone;
    }

    private Map<Long, Color> getColors() {
        return jdbcTemplate.query(SQL_QUERY_FOR_GETTING_ALL_COLORS,
                new BeanPropertyRowMapper<>(Color.class)).stream().collect(Collectors.toMap(Color::getId, (c) -> c));
    }

    public List<Phone> findAllWithPositiveStock(int offset, int limit) {
        Map<Long, Color> colors = getColors();
        List<Phone> phones = jdbcTemplate.query(SQL_FOR_GETTING_AVAILABLE_PHONES_BY_OFFSET_AND_LIMIT,
                        phoneBeanPropertyRowMapper, offset, limit);
        for (Phone phone : phones) {
            setColorsForPhone(phone, colors);
        }
        return phones;
    }


    @Override
    public List<Phone> findAllByKeyword(String keyword) {
        Map<Long, Color> colors = getColors();
        List<Phone> phones = jdbcTemplate.query(SQL_FOR_GETTING_PHONES_BY_KEYWORD,
                phoneBeanPropertyRowMapper, keyword, keyword);
        for (Phone phone : phones) {
            setColorsForPhone(phone, colors);
        }
        return phones;
    }

    private void setColorsForPhone(Phone phone, Map<Long, Color> colors) {
        phone.setColors(new HashSet<>());
        jdbcTemplate.query(SQL_FOR_GETTING_COLORS_BY_PHONE_ID,
                        (resultSet, i) -> phone.getColors().add(colors.get(resultSet.getLong("colorId"))), phone.getId());
    }

    @Override
    public Stock getStockFor(Long key) {
        Stock stock = new Stock();
        jdbcTemplate.query(SQL_FOR_GETTING_STOCK_BY_PHONE_ID, (resultSet) -> {stock.setStock(resultSet.getInt("stock"));
                                                                                stock.setReserved(resultSet.getInt("reserved"));
                                                                                stock.setPhone(get(key).get());}, key);
        return stock;
    }

    @Override
    public Long getTotalAmountOfAvailablePhones() {
        return jdbcTemplate.queryForObject(SQL_FOR_GETTING_TOTAL_AMOUNT_OF_AVAILABLE, Long.class);
    }

    @Override
    public void makeReservationFor(Long key, Integer quantity) {
        jdbcTemplate.update(SQL_FOR_CHANGE_RESERVED_QUANTITY, quantity, key);
    }

    @Override
    public void removeReservationFor(Long key, Integer quantity) {
        makeReservationFor(key, -quantity);
    }

    private Map<String, Object> getPhoneValues(Phone phone) {
        Map<String, Object> values = new HashMap<>();
        values.put("id", phone.getId());
        values.put("brand", phone.getBrand());
        values.put("model", phone.getModel());
        values.put("price", phone.getPrice());
        values.put("displaySizeInches", phone.getDisplaySizeInches());
        values.put("weightGr", phone.getWeightGr());
        values.put("lengthMm", phone.getLengthMm());
        values.put("widthMm", phone.getWidthMm());
        values.put("heightMm", phone.getHeightMm());
        values.put("announced", phone.getAnnounced());
        values.put("deviceType", phone.getDeviceType());
        values.put("os", phone.getOs());
        values.put("displayResolution", phone.getDisplayResolution());
        values.put("pixelDensity", phone.getPixelDensity());
        values.put("displayTechnology", phone.getDisplayTechnology());
        values.put("backCameraMegapixels", phone.getBackCameraMegapixels());
        values.put("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        values.put("ramGb", phone.getRamGb());
        values.put("internalStorageGb", phone.getInternalStorageGb());
        values.put("batteryCapacityMah", phone.getBatteryCapacityMah());
        values.put("talkTimeHours", phone.getTalkTimeHours());
        values.put("standByTimeHours", phone.getStandByTimeHours());
        values.put("bluetooth", phone.getBluetooth());
        values.put("positioning", phone.getPositioning());
        values.put("imageUrl", phone.getImageUrl());
        values.put("description", phone.getDescription());
        return values;
    }
}
