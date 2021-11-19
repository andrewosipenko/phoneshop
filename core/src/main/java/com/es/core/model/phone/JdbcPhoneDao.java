package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_PHONE_BY_ID_QUERY = "select * from phones where id = ?";
    private static final String SELECT_COLOR_ID_BY_PHONE_ID_QUERY = "select colorId from phone2color where phoneId = ?";
    private static final String SELECT_COLOR_BY_ID_QUERY = "select * from colors where id = ?";
    private static final String INSERT_PHONE_QUERY = "insert into phones values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_PHONE2COLOR_QUERY = "insert into phone2color values (?, ?)";

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone = Optional.ofNullable(jdbcTemplate
                .queryForObject(SELECT_PHONE_BY_ID_QUERY, new Object[]{key},
                        BeanPropertyRowMapper.newInstance(Phone.class)));
        if (phone.isPresent()) {
            List<Long> colorIds = jdbcTemplate.queryForList(SELECT_COLOR_ID_BY_PHONE_ID_QUERY,
                    new Object[]{key}, Long.class);
            Set<Color> colors = colorIds.stream()
                    .map(colorId -> jdbcTemplate.queryForObject(SELECT_COLOR_BY_ID_QUERY,
                            new Object[]{colorId}, BeanPropertyRowMapper.newInstance(Color.class)))
                    .collect(Collectors.toSet());
            phone.get().setColors(colors);
        }
        return phone;
    }

    public void save(final Phone phone) {
        jdbcTemplate.update(INSERT_PHONE_QUERY,
                phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription());
        jdbcTemplate.batchUpdate(INSERT_INTO_PHONE2COLOR_QUERY, phone.getColors(), phone.getColors().size(),
                (preparedStatement, color) -> {
                    preparedStatement.setLong(1, phone.getId());
                    preparedStatement.setLong(2, color.getId());
                });
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }
}
