package com.es.core.model.phone;


import com.es.core.model.color.Color;
import com.es.core.model.enums.SortField;
import com.es.core.model.enums.SortOrder;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.exception.StockNotFindException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    public static final String SELECT_FROM_STOCKS_WHERE_PHONE_ID = "select * from stocks where phoneId=?";
    public static final String SELECT_COLOR_ID_FROM_PHONE_2_COLOR_WHERE_PHONE_ID = "select colorId from phone2color where phoneId=?";
    public static final String SELECT_FROM_COLORS_WHERE_ID = "select * from colors where id=?";
    public static final String SELECT_FROM_PHONES_WHERE_ID = "select * from phones where id=?";
    public static final String INSERT_INTO_PHONES_VALUES = "insert into phones values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SELECT_MAX_ID_FROM_PHONES = "select max(id) from phones";
    public static final String INSERT_INTO_PHONE_2_COLOR_VALUES = "insert into phone2color values (?, ?)";
    public static final String SELECT_FROM_PHONES_WHERE_POSITIVE_STOCK_OFFSET_LIMIT = "select * from phones inner join stocks on phones.id = stocks.phoneId where stock - reserved > 0 offset ? limit ?";
    public static final String SELECT_VALID_PHONES = "select * from phones inner join stocks on phones.id = stocks.phoneId where (stock - reserved > 0) and (displaySizeInches > 0) and (price > 0) and ((lower(model) like '%";
    public static final String OR_LOWER_BRAND_LIKE = "%') or (lower(brand) like '%";
    public static final String END = "%'))";
    public static final String LIMIT = " limit ";
    public static final String ORDER_BY = " order by ";
    public static final String OFFSET = " offset ";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) throws PhoneNotFindException {
        Set<Color> colorSet = getColorsByPhoneId(key);

        Optional<Phone> phone = jdbcTemplate.query(SELECT_FROM_PHONES_WHERE_ID, new Object[]{key},
                new PhoneRowMapper()).stream().findAny();
        if (phone.isPresent()) {
            phone.get().setColors(colorSet);
        } else {
            throw new PhoneNotFindException(key);
        }
        phone.ifPresent(value -> value.setColors(colorSet));
        return phone;
    }

    public void save(final Phone phone) {
        Long phoneIndex = jdbcTemplate.queryForObject(SELECT_MAX_ID_FROM_PHONES, Long.class) + 1;
        phone.setId(phoneIndex);

        jdbcTemplate.update(INSERT_INTO_PHONES_VALUES, getPhoneFieldsObject(phone, phoneIndex));

        List<Color> colorList = phone.getColors().stream().toList();
        jdbcTemplate.batchUpdate(INSERT_INTO_PHONE_2_COLOR_VALUES, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, phoneIndex);
                preparedStatement.setLong(2, colorList.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return colorList.size();
            }
        });
    }

    private Object[] getPhoneFieldsObject(Phone phone, Long index) {
        return new Object[]{
                index,
                phone.getBrand(),
                phone.getModel(),
                phone.getPrice(),
                phone.getDisplaySizeInches(),
                phone.getWeightGr(),
                phone.getLengthMm(),
                phone.getWidthMm(),
                phone.getHeightMm(),
                phone.getAnnounced(),
                phone.getDeviceType(),
                phone.getOs(),
                phone.getDisplayResolution(),
                phone.getPixelDensity(),
                phone.getDisplayTechnology(),
                phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(),
                phone.getRamGb(),
                phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(),
                phone.getStandByTimeHours(),
                phone.getBluetooth(),
                phone.getPositioning(),
                phone.getImageUrl(),
                phone.getDescription(),
        };
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phoneList = jdbcTemplate.query(SELECT_FROM_PHONES_WHERE_POSITIVE_STOCK_OFFSET_LIMIT, new Object[]{offset, limit}, new PhoneRowMapper());
        phoneList.forEach(phone -> phone.setColors(getColorsByPhoneId(phone.getId())));
        return phoneList;
    }

    public List<Phone> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String searchText) {
        List<Phone> phoneList = jdbcTemplate.query(createSqlPhoneFilter(offset, limit, sortField, sortOrder, searchText), new PhoneRowMapper());
        phoneList.forEach(phone -> phone.setColors(getColorsByPhoneId(phone.getId())));
        return phoneList;
    }

    private String createSqlPhoneFilter(int offset, int limit, SortField sortField, SortOrder sortOrder, String searchText) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(SELECT_VALID_PHONES)
                .append(searchText.toLowerCase(Locale.ROOT)).append(OR_LOWER_BRAND_LIKE).append(searchText.toLowerCase(Locale.ROOT)).append(END)
                .append(ORDER_BY)
                .append(sortField).append(" ")
                .append(sortOrder).append(" ")
                .append(OFFSET).append(offset)
                .append(LIMIT).append(limit)
                .toString();
    }

    public Optional<Color> getColor(final Long key) {
        return jdbcTemplate.query(SELECT_FROM_COLORS_WHERE_ID,
                new Object[]{key}, new BeanPropertyRowMapper<>(Color.class)).stream().findAny();
    }

    public Set<Color> getColorsByPhoneId(final Long key) {
        List<Long> colorsIds = jdbcTemplate.queryForList(SELECT_COLOR_ID_FROM_PHONE_2_COLOR_WHERE_PHONE_ID,
                new Object[]{key}, Long.class);
        return colorsIds.stream()
                .map(this::getColor)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    public Optional<Stock> getStock(final Long key) {
        Optional<Stock> stock = jdbcTemplate.query(SELECT_FROM_STOCKS_WHERE_PHONE_ID,
                new Object[]{key}, new StockRowMapper()).stream().findAny();
        Optional<Phone> optionalPhone = get(key);
        if (optionalPhone.isPresent()) {
            Phone phone = optionalPhone.get();

            if (stock.isPresent()) {
                stock.get().setPhone(phone);
                return stock;
            } else {
                throw new StockNotFindException(key);
            }
        } else {
            throw new PhoneNotFindException(key);
        }
    }
}
