package com.es.core.dao.phone;

import com.es.core.dao.color.ColorDao;
import com.es.core.model.phone.Phone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPhoneDao implements PhoneDao {
    private final static String QUERY_FOR_FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT =
            "select * from phones offset ? limit ?";
    private final static String QUERY_FOR_FIND_PHONE_BY_ID =
            "select * from phones where id = ?";
    private final static String QUERY_TO_SAVE_PHONE =
            "merge into phones (brand, model, price, displaySizeInches, weightGr, lengthMm, " +
                    "widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
                    "displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, " +
                    "batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
                    "description) key(brand, model) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String QUERY_TO_FIND_PHONE_ID =
            "select id from phones where brand = ? and model = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ColorDao colorDao;

    public Optional<Phone> get(Long key) {
        Optional<Phone> optionalPhone;
        try {
            Phone phone = jdbcTemplate
                    .queryForObject(QUERY_FOR_FIND_PHONE_BY_ID, new BeanPropertyRowMapper<>(Phone.class), key);
            phone.setColors(colorDao.findColorsToPhone(key));

            optionalPhone = Optional.of(phone);
        } catch (EmptyResultDataAccessException e) {
            optionalPhone = Optional.empty();
        }
        return optionalPhone;
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(QUERY_FOR_FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT,
                new BeanPropertyRowMapper(Phone.class), offset, limit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Phone phone) {
        jdbcTemplate.update(QUERY_TO_SAVE_PHONE,
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
                phone.getDescription()
        );

        if (phone.getId() == null) {
            Long id = jdbcTemplate.queryForObject(QUERY_TO_FIND_PHONE_ID, Long.class, phone.getBrand(), phone.getModel());
            phone.setId(id);
        }

        colorDao.savePhoneColors(phone);
    }
}