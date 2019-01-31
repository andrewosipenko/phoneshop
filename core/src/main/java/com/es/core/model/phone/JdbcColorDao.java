package com.es.core.model.phone;

import com.es.core.model.phone.mappers.ColorExtractor;
import com.es.core.utils.InjectLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("jdbcColorDao")
public class JdbcColorDao implements ColorDao {

    private final static String SQL_DELETE_COLORS_QUERY = "delete from phone2color where phoneId = ?";
    private final String SQL_UPDATE_COLOR_QUERY = "update colors set code = ? where colors.id = ?;";
    private final String SQL_ADD_COLORS_TO_PHONE_QUERY = "INSERT INTO phone2color (phoneId, colorId) " +
            "values (?, ?)";
    private final String SQL_COUNT_OF_COLOR_QUERY = "select count(*) from colors where colors.id = ?;";

    private final String SQL_GET_COLORS_QUERY = "select * from colors " +
            "inner join phone2color " +
            "on colors.id = phone2color.colorId " +
            "where phone2color.phoneId = ?";

    private final String SQL_GET_COLOR_BY_ID_QUERY = "select * from colors where id = ?";

    @InjectLogger
    private Log LOG;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcColorDao(JdbcTemplate jdbcTemplate,
                        SimpleJdbcInsert simpleJdbcInsert,
                        NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Fetchs a set of phone colors from database
     * @param key
     * @return
     */
    public Set<Color> getPhoneColors(final Long key) {
        return jdbcTemplate.query(SQL_GET_COLORS_QUERY,
                new ColorExtractor(), key);
    }

    private void addColor(final Color color) {
        Long id = simpleJdbcInsert
                .withTableName("colors")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(color))
                .longValue();
        color.setId(id);
    }

    private void updateColor(final Color color) {
        jdbcTemplate.update(SQL_UPDATE_COLOR_QUERY,
                color.getCode(), color.getId());
    }

    private void deletePhoneColors(final Set<Color> existingColors,
                                   final Set<Color> colors, final Long phoneId) {

        List<Object[]> deleteParams = existingColors.parallelStream()
                .filter(item -> !colors.contains(item))
                .map(item -> new Object[]{item.getId()})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(SQL_DELETE_COLORS_QUERY, deleteParams);

    }

    private void addNotExistingPhoneColors(final Set<Color> existingColors,
                                           final Set<Color> colors, final Long phoneId) {

        List<Object[]> insertParams = colors.parallelStream()
                .filter(item -> !existingColors.contains(item))
                .map(color -> new Object[]{phoneId, color.getId()})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(SQL_ADD_COLORS_TO_PHONE_QUERY, insertParams);

    }

    /**
     * Persists a phone <-> colors junction
     * @param colors a set of colors the phone have
     * @param phoneId id of the phone where we want to add colors
     */
    public void addColorsToPhone(final Set<Color> colors, final Long phoneId) {
        Set<Color> existingColors = getPhoneColors(phoneId);

        deletePhoneColors(existingColors, colors, phoneId);
        addNotExistingPhoneColors(existingColors, colors, phoneId);

        LOG.info(String.format("Colors of the phone with id: %s successfully updated", phoneId));
    }

    @Override
    public Optional<Color> get(Long key) {
        try{
            Color color = jdbcTemplate.queryForObject(SQL_GET_COLOR_BY_ID_QUERY, new Object[]{ key },
                    new BeanPropertyRowMapper<>(Color.class));

            Optional<Color> result = Optional.of(color);

            return result;
        }
        catch (EmptyResultDataAccessException e){

            LOG.error("Can't load color data with id: " + (key == null ? "null" : key.toString()));

            return Optional.empty();
        }
    }

    private Long getAmountOfColorInDatabase(final Long id) {
        //LOG.info("Checking existing phone in database with id: " + id);
        if(id == null) return 0L;
        return jdbcTemplate.queryForObject(SQL_COUNT_OF_COLOR_QUERY,
                new Object[]{ id }, Long.class);
    }

    public void saveColor(Color color) {
        Optional<Long> id = Optional.of(color).map(item -> color.getId());
        if(id.isPresent()) {
            id.map(this::getAmountOfColorInDatabase)
                    .ifPresent(count -> {
                        if (count > 0) {
                            updateColor(color);
                            LOG.info("Contact updated with id: " + color.getId());
                        } else {
                            LOG.info("Adding new color");
                            addColor(color);
                            LOG.info("New contact inserted with id: " + color.getId());
                        }
                    } );
        }
        else {
            addColor(color);
        }
    }
}
