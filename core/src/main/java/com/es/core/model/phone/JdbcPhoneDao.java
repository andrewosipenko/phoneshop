package com.es.core.model.phone;

import com.es.core.utils.InjectLogger;
import org.apache.commons.logging.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcPhoneDao implements PhoneDao {
    private final static String SQL_GET_PHONE_BY_ID_QUERY = "select * from phones where id = ?";
    private final static String SQL_FIND_ALL_QUERY = "select * from phones offset :offset_number limit :limit_number";
    private final static String SQL_COUNT_OF_PHONE_QUERY = "select count(*) from phones where phones.id = ?;";
    private final static String SQL_UPDATE_PHONE_QUERY = "update phones set" +
            " brand = ?," +
            " model = ?," +
            " price = ?," +
            " displaySizeInches = ?," +
            " weightGr = ?," +
            " lengthMm = ?," +
            " widthMm = ?," +
            " heightMm = ?," +
            " announced = ?," +
            " deviceType = ?," +
            " os = ?," +
            " displayResolution = ?," +
            " pixelDensity = ?," +
            " displayTechnology = ?," +
            " backCameraMegapixels = ?," +
            " frontCameraMegapixels = ?," +
            " ramGb = ?," +
            " internalStorageGb = ?," +
            " batteryCapacityMah = ?," +
            " talkTimeHours = ?," +
            " standByTimeHours = ?," +
            " bluetooth = ?," +
            " positioning = ?," +
            " imageUrl = ?," +
            " description = ?" +
            " where phones.id = ?;";

    private final ColorDao colorDao;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectLogger
    private static Log LOG;

    public JdbcPhoneDao(ColorDao colorDao,
                        JdbcTemplate jdbcTemplate,
                        SimpleJdbcInsert simpleJdbcInsert,
                        NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.colorDao = colorDao;
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Phone> get(Long key) {
        try{
            Phone phone = jdbcTemplate.queryForObject(SQL_GET_PHONE_BY_ID_QUERY, new Object[]{ key },
                    new BeanPropertyRowMapper<>(Phone.class));
            Optional<Phone> result = Optional.of(phone);
            result.ifPresent(item -> phone.setColors(colorDao.getPhoneColors(key)));
            LOG.info("A phone object from db loaded with id: " + (key == null ? "null" : key.toString()));
            return result;
        }
        catch (EmptyResultDataAccessException e){
            LOG.error("Can't find phone data with id: " + (key == null ? "null" : key.toString()));
            return Optional.empty();
        }
    }

    public void save(Phone phone) {
        Optional<Long> id = Optional.of(phone).map(item -> phone.getId());
        if(id.isPresent()) {
            id.map(this::getAmountOfPhoneInDatabase)
            .ifPresent(count -> {
                if (count > 0) {
                    update(phone);
                    LOG.info("Contact updated with id: " + phone.getId());
                } else {
                    addPhone(phone);
                    LOG.info("New contact inserted with id: " + phone.getId());
                }
            } );
        }
        else {
            addPhone(phone);
            LOG.info("New contact inserted with id: " + phone.getId());
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        final Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("offset_number", offset);
        namedParameters.put("limit_number", limit);
        List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_ALL_QUERY, namedParameters,
                new BeanPropertyRowMapper<>(Phone.class));
        phones.parallelStream().forEach(item -> item.setColors(colorDao.getPhoneColors(item.getId())));
        return phones;
    }

    private void addPhone(Phone phone) {
        Long id = simpleJdbcInsert
                .withTableName("phones")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(phone))
                .longValue();
        phone.setId(id);
        colorDao.addColorsToPhone(phone.getColors(), id);
    }

    private void update(Phone phone) {
        jdbcTemplate.update(SQL_UPDATE_PHONE_QUERY,
                phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(),
                phone.getImageUrl(), phone.getDescription(), phone.getId());
        colorDao.addColorsToPhone(phone.getColors(), phone.getId());

    }

    private Long getAmountOfPhoneInDatabase(Long id) {
        LOG.info("Checking existing phone in database with id: " + id);
        if(id == null) return 0L;
        return jdbcTemplate.queryForObject(SQL_COUNT_OF_PHONE_QUERY,
                new Object[]{ id }, Long.class);
    }
}
