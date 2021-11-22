package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao {
    public static final String SELECT_COLOR_ID_FROM_PHONE_2_COLOR_WHERE_PHONE_ID = "select colorId from phone2color where phoneId=?";
    public static final String SELECT_FROM_COLORS_WHERE_ID = "select * from colors where id=?";
    public static final String SELECT_FROM_PHONES_WHERE_ID = "select * from phones where id=?";
    public static final String INSERT_INTO_PHONES_VALUES = "insert into phones values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SELECT_MAX_ID_FROM_PHONES = "select max(id) from phones";
    public static final String INSERT_INTO_PHONE_2_COLOR_VALUES = "insert into phone2color values (?, ?)";
    public static final String SELECT_FROM_PHONES_OFFSET = "select * from phones offset ";
    public static final String LIMIT = " limit ";
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        List<Integer> colorIds =
                jdbcTemplate.queryForList(SELECT_COLOR_ID_FROM_PHONE_2_COLOR_WHERE_PHONE_ID,
                        new Object[]{key},
                        Integer.class);

        Set<Color> colorSet = new HashSet<>();
        for (Integer colorId : colorIds) {
            Optional<Color> color = jdbcTemplate.query(SELECT_FROM_COLORS_WHERE_ID,
                    new Object[]{colorId},
                    new ColorRowMapper()).stream().findAny();
            color.ifPresent(colorSet::add);
        }

        Optional<Phone> phone = jdbcTemplate.query(SELECT_FROM_PHONES_WHERE_ID,
                        new Object[]{key},
                        new PhoneRowMapper())
                .stream().findAny();
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
        return jdbcTemplate.query(SELECT_FROM_PHONES_OFFSET + offset + LIMIT + limit, new PhoneRowMapper());
    }
}
