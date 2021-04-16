package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String PHONE_BY_ID = "select * from phones p left join phone2color p2c on p.id = p2c.phoneId left join colors c on p2c.colorId = c.id where p.id = ?";
    private static final String ALL_PHONES = "select * from (select * from phones limit ? offset ?) p left join phone2color p2c on p.id = p2c.phoneId left join colors c on p2c.colorId = c.id";
    private static final String INSERT_PHONE = "insert into phones values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PHONE = "update phones set brand = ?, model = ?, price = ?, displaySizeInches = ?, weightGr = ?, lengthMm = ?, widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, os = ?, displayResolution = ?, pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, frontCameraMegapixels = ?, ramGb = ?, internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, standByTimeHours = ?, bluetooth = ?, positioning = ?, imageUrl = ?, description = ? where id = ?";
    private static final String PHONE2COLOR_BY_PHONE = "select * from phone2color where phoneId = ?";
    private static final String INSERT_PHONE2COLOR = "insert into phone2color values (?, ?)";
    private static final String DELETE_PHONE2COLOR = "delete from phone2color where phoneId = ? and colorId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        return jdbcTemplate.query(PHONE_BY_ID, new PhoneResultSetExtractor(), key).stream().findAny();
    }

    public void save(final Phone phone) {
        Optional<Phone> optionalPhone = get(phone.getId());
        if (optionalPhone.isPresent()) {
            jdbcTemplate.update(UPDATE_PHONE, phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription(), phone.getId());
            List<Color> colors = jdbcTemplate.query(PHONE2COLOR_BY_PHONE, new BeanPropertyRowMapper<>(Color.class), phone.getId());
            colors.forEach(c -> {
                if (!phone.getColors().contains(c)) {
                    jdbcTemplate.update(DELETE_PHONE2COLOR, phone.getId(), c.getId());
                }
            });
            phone.getColors().forEach(c -> {
                if (!colors.contains(c)) {
                    jdbcTemplate.update(INSERT_PHONE2COLOR, phone.getId(), c.getId());
                }
            });
        } else {
            jdbcTemplate.update(INSERT_PHONE);
            phone.getColors().forEach(c -> jdbcTemplate.update(INSERT_PHONE2COLOR, phone.getId(), c.getId()));
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(ALL_PHONES, new PhoneResultSetExtractor(), limit, offset);
    }
}
