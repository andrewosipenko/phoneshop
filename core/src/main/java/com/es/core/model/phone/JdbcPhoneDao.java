package com.es.core.model.phone;

import com.es.core.exception.ArgumentIsNullException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String PHONE_BY_ID = "select * from phones p left join phone2color p2c on p.id = p2c.phoneId left join colors c on p2c.colorId = c.id where p.id = ?";
    private static final String ALL_PHONES = "select * from (select * from phones limit ? offset ?) p left join phone2color p2c on p.id = p2c.phoneId left join colors c on p2c.colorId = c.id";
    private static final String INSERT_PHONE = "insert into phones(brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PHONE = "update phones set brand = ?, model = ?, price = ?, displaySizeInches = ?, weightGr = ?, lengthMm = ?, widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, os = ?, displayResolution = ?, pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, frontCameraMegapixels = ?, ramGb = ?, internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, standByTimeHours = ?, bluetooth = ?, positioning = ?, imageUrl = ?, description = ? where id = ?";
    private static final String COLOR_BY_PHONE = "select colorId from phone2color where phoneId = ?";
    private static final String INSERT_PHONE2COLOR = "insert into phone2color values (?, ?)";
    private static final String DELETE_PHONE2COLOR = "delete from phone2color where phoneId = ? and colorId = ?";
    private static final String GET_PHONE_ID = "select max(id) from phones";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        return jdbcTemplate.query(PHONE_BY_ID, new PhoneResultSetExtractor(), key).stream().findAny();
    }

    public void save(final Phone phone) {
        if (phone == null || phone.getBrand() == null || phone.getModel() == null) {
            throw new ArgumentIsNullException();
        }
        Optional<Phone> optionalPhone = Optional.empty();
        if (phone.getId() != null) {
            optionalPhone = get(phone.getId());
        }
        if (optionalPhone.isPresent()) {
            jdbcTemplate.update(UPDATE_PHONE, phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription(), phone.getId());
            List<Long> colors = jdbcTemplate.query(COLOR_BY_PHONE, new SingleColumnRowMapper<>(), phone.getId());
            colors.forEach(c -> {
                if (!phone.getColors().stream()
                        .map(Color::getId)
                        .collect(Collectors.toList())
                        .contains(c)) {
                    jdbcTemplate.update(DELETE_PHONE2COLOR, phone.getId(), c);
                }
            });
            phone.getColors().stream()
                    .map(Color::getId)
                    .collect(Collectors.toList())
                    .forEach(c -> {
                        if (!colors.contains(c)) {
                            jdbcTemplate.update(INSERT_PHONE2COLOR, phone.getId(), c);
                        }
                    });
        } else {
            jdbcTemplate.update(INSERT_PHONE, phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription());
            Long phoneId = jdbcTemplate.queryForObject(GET_PHONE_ID, Long.class);
            phone.getColors().forEach(c -> jdbcTemplate.update(INSERT_PHONE2COLOR, phoneId, c.getId()));
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(ALL_PHONES, new PhoneResultSetExtractor(), limit, offset);
    }
}
