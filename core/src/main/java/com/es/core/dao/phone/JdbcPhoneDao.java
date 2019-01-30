package com.es.core.dao.phone;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private final String REGEX_TO_ADD_ARRAY_PARAMETER = "arrayParameter";

    private final static String QUERY_FOR_FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT =
            "select * from phones offset ? limit ?";
    private final static String QUERY_FOR_FIND_PHONE_BY_ID =
            "select * from phones where id = ?";
    private final static String QUERY_FOR_FIND_COLORS_BY_PHONE_ID =
            "select * from colors where id IN (select colorId from phone2color where phoneId = ?)";
    private final static String QUERY_TO_SAVE_PHONE =
            "merge into phones (brand, model, price, displaySizeInches, weightGr, lengthMm, " +
                    "widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
                    "displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, " +
                    "batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
                    "description) key(brand, model) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String QUERY_TO_FIND_PHONE_ID =
            "select id from phones where brand = ? and model = ?";
    private final static String QUERY_TO_DELETE_OLD_PHONE_COLORS =
            "delete from phone2color where phoneId = ? and colorId in (arrayParameter)";
    private final static String QUERY_TO_MERGE_NEW_COLOR =
            "merge into phone2color (phoneId, colorId) key(phoneId, colorId) values(?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        Optional<Phone> optionalPhone;

        try {
            Phone phone = jdbcTemplate
                    .queryForObject(QUERY_FOR_FIND_PHONE_BY_ID, new BeanPropertyRowMapper<>(Phone.class), key);
            phone.setColors(findColorsToPhone(key));
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
    public void save(final Phone phone) {
        savePhone(phone);
        if (phone.getId() == null) {
            setupIdToPhone(phone);
        }
        saveColors(phone);
    }

    private void savePhone(Phone phone) {
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
    }

    private void setupIdToPhone(Phone phone) {
        Long id = jdbcTemplate.queryForObject(QUERY_TO_FIND_PHONE_ID, Long.class, phone.getBrand(), phone.getModel());
        phone.setId(id);
    }

    private void saveColors(Phone phone) {
        Set<Color> oldColors = findColorsToPhone(phone.getId());
        Set<Color> newColors = phone.getColors();

        long[] colorsIdToDelete = oldColors.stream()
                .filter(oldColor -> newColors.stream()
                        .noneMatch(oldColor::equals))
                .mapToLong(Color::getId).toArray();

        String qeryToDeleteOldColors = addSetParametersToQuery(colorsIdToDelete, QUERY_TO_DELETE_OLD_PHONE_COLORS);
        jdbcTemplate.update(qeryToDeleteOldColors, phone.getId());

        for (Color color : newColors) {
            jdbcTemplate.update(QUERY_TO_MERGE_NEW_COLOR, phone.getId(), color.getId());
        }
    }

    private Set<Color> findColorsToPhone(final Long key) {
        return new HashSet<>(jdbcTemplate
                .query(QUERY_FOR_FIND_COLORS_BY_PHONE_ID, new BeanPropertyRowMapper<>(Color.class), key));
    }

    private String addSetParametersToQuery(long[] arrayParameters, String query) {
        String stringParameter = Arrays.toString(arrayParameters);
        stringParameter = stringParameter.substring(1, stringParameter.length() - 1);

        return query.replace(REGEX_TO_ADD_ARRAY_PARAMETER, stringParameter);
    }
}