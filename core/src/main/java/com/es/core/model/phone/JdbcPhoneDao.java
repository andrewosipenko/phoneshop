package com.es.core.model.phone;

import com.es.core.model.phone.mappers.ColorExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao{
    private final String SQL_GET_PHONE_BY_ID_QUERY = "select * from phones where id = ?";

    private final String SQL_GET_COLORS_QUERY = "select * from colors " +
            "inner join phone2color " +
            "on colors.id = phone2color.colorId " +
            "where phone2color.phoneId = ?";

    private final String SQL_FIND_ALL_QUERY = "select * from phones offset :offset_number limit :limit_number";

    private final String SQL_COUNT_OF_PHONE_QUERY = "select count(*) from phones where phones.id = ?;";

    private final String SQL_ADD_COLORS_TO_PHONE_QUERY = "INSERT INTO phone2color (phoneId, colorId) " +
            "    SELECT phoneId, colorId " +
            "    FROM (SELECT ? as phoneId, ? as colorId) t " +
            "    WHERE NOT EXISTS (SELECT * FROM phone2color u WHERE " +
            "u.phoneId = t.phoneId and u.colorId = t.colorId);";
    private final String SQL_UPDATE_PHONE_QUERY = "update phones set" +
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



    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcPhoneDao(JdbcTemplate jdbcTemplate,
                        SimpleJdbcInsert simpleJdbcInsert,
                        NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Returns a Phone object from database
     * @param key
     * @return
     */
    public Optional<Phone> get(final Long key) {

        try{
            Phone phone = jdbcTemplate.queryForObject(SQL_GET_PHONE_BY_ID_QUERY, new Object[]{ key },
                    new BeanPropertyRowMapper<>(Phone.class));

            Optional<Phone> result = Optional.of(phone);
            result.ifPresent(item -> phone.setColors(getColors(key)));

            return Optional.of(phone);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    /**
     * Adds object to db if it not exists
     * or updates object in database
     * @param phone a phone entity that we want to save
     */
    public void save(final Phone phone) {
        Optional.of(phone)
                .map(item -> phone.getId())
                .map(this::getAmountOfPhoneInDatabase)
                .ifPresent(count -> {
                    if (count > 0) {
                        update(phone);
                    } else {
                        addPhone(phone);
                    }
                } );
    }

    /**
     * Get's all phones in page
     * @param offset
     * @param limit
     * @return
     */
    public List<Phone> findAll(int offset, int limit) {
        final Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("offset_number", offset);
        namedParameters.put("limit_number", limit);

        List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_ALL_QUERY, namedParameters,
                new BeanPropertyRowMapper<>(Phone.class));

        phones.parallelStream().forEach(item -> item.setColors(getColors(item.getId())));

        return phones;
    }

    /**
     * Adds new phone to database
     * @param phone
     */
    private void addPhone(final Phone phone) {
        final Long id = simpleJdbcInsert
                .executeAndReturnKey(new BeanPropertySqlParameterSource(phone))
                .longValue();
        addColorsToPhone(phone.getColors(), id);
    }

    /**
     * Updates a phone entity in database
     * @param phone
     */
    private void update(final Phone phone) {

        jdbcTemplate.update(SQL_UPDATE_PHONE_QUERY, new Object[]{ phone.getId() },
                phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(),
                phone.getImageUrl(), phone.getDescription());
        addColorsToPhone(phone.getColors(), phone.getId());

    }

    /**
     * Persists a phone <-> colors junction
     * @param colors a set of colors the phone have
     * @param phoneId id of the phone where we want to add colors
     */
    private void addColorsToPhone(final Set<Color> colors, final Long phoneId) {
        List<Object[]> insertParams = colors.parallelStream()
                .map(color -> new Object[]{phoneId, color.getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(SQL_ADD_COLORS_TO_PHONE_QUERY, insertParams);
    }

    /**
     * Checks how many rows in database with this id
     * @param id of the checking phone
     * @return
     */
    private Long getAmountOfPhoneInDatabase(final Long id) {
        return jdbcTemplate.queryForObject(SQL_COUNT_OF_PHONE_QUERY,
                new Object[]{ id }, Long.class);
    }

    /**
     * Fetchs a set of phone colors from database
     * @param key
     * @return
     */
    private Set<Color> getColors(final Long key) {
        return jdbcTemplate.query(SQL_GET_COLORS_QUERY,
                new ColorExtractor(), key);
    }
}
