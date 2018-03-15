package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public abstract class AbstractJdbcPhoneDao {
    @Resource
    JdbcTemplate jdbcTemplate;

    void insert(Phone phone) {
        Number newId = jdbcTemplate.queryForObject(PhoneQueries.COUNT_PHONES_QUERY, Integer.class) + 1000;
        phone.setId(newId.longValue());
        jdbcTemplate.update(PhoneQueries.INSERT_PHONE_QUERY,getInsertParameters(phone));
    }

    void update(final Phone phone) {
        jdbcTemplate.update(PhoneQueries.UPDATE_PHONE_QUERY, getUpdateParameters(phone));
        jdbcTemplate.update(PhoneQueries.DELETE_COLORS_QUERY, phone.getId());
        insertColors(phone);
    }

    private void insertColors(final Phone phone) {
        jdbcTemplate.batchUpdate(PhoneQueries.INSERT_COLORS_QUERY, getPhone2ColorSetter(phone));
    }

    private Object[] getUpdateParameters(final Phone phone) {
        return new Object[] {
                phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription(), phone.getId()
        };
    }

    private Object[] getInsertParameters(final Phone phone) {
        return new Object[] {
                phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription()
        };
    }

    private BatchPreparedStatementSetter getPhone2ColorSetter(final Phone phone) {
        return new BatchPreparedStatementSetter() {
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
        };
    }

    String getFindAllQueryString(int offset, int limit) {
        return String.format(PhoneQueries.FIND_ALL_QUERY, insertOffsetAndLimit(offset, limit));
    }

    private String insertOffsetAndLimit(int offset, int limit) {
        StringBuilder limitBuilder = new StringBuilder();
        if (limit > 0) {
            limitBuilder.append("LIMIT")
                    .append(offset >  0 ? String.format(" %d, ", offset) : " ")
                    .append(limit);
        }
        return limitBuilder.toString();
    }

    Set<Color> getPhoneColors(List<Phone> phoneOfDifferentColors) {
        Set<Color> colors = new HashSet<>();
        phoneOfDifferentColors.forEach(e -> {
            Color color = e.getColors().iterator().next();
            colors.add(new Color(color.getId(), color.getCode()));
        });
        return  colors;
    }
}
