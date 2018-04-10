package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Phone> get(final Long key) {

        List<Phone> phoneOfDifferentColors = jdbcTemplate.query(PhoneQueries.GET_BY_KEY_QUERY, new Object[] {key}, new PhoneRowMapper());
        if(phoneOfDifferentColors.size() == 0) {
            return Optional.empty();
        }
        Phone phone = phoneOfDifferentColors.get(0);

        phone.setColors(getPhoneColors(phoneOfDifferentColors));
        phone.setId(key);
        return Optional.of(phone);
    }

    public void save(Phone phone) {
        if(phone.getId() == null) {
            insert(phone);
        } else {
            update(phone);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return findAllInOrder(offset, limit, OrderEnum.BRAND, null);
    }

    public Long getPhonesCount() {
        return jdbcTemplate.queryForObject(PhoneQueries.COUNT_PHONES_QUERY, Long.class);
    }

    public Long getPhonesCountByQuery(String searchQueryString) {
        if(searchQueryString == null || searchQueryString.equals(""))
            return getPhonesCount();
        return jdbcTemplate.queryForObject(PhoneQueries.COUNT_PHONES_BY_SEARCH_QUERY, Long.class, searchQueryString.toLowerCase(), searchQueryString.toLowerCase());
    }

    public List<Phone> findAllInOrder(int offset, int limit, OrderEnum order, String searchQueryString) {
        List<Phone> phones;
        if(searchQueryString == null || searchQueryString.length() == 0) {
             phones = jdbcTemplate.query( getFindAllInOrderQueryString(offset, limit, order), new BeanPropertyRowMapper<>(Phone.class));
        } else {
            phones = jdbcTemplate.query(getSearchForAllInOrderQueryString(offset, limit, order), new BeanPropertyRowMapper<>(Phone.class),
                    searchQueryString.toLowerCase(), searchQueryString.toLowerCase());
        }
        for (Phone phone : phones) {
            setColors(phone);
        }
        return phones;
    }

    private void setColors(Phone phone) {
        List<Integer> colorIds = jdbcTemplate.queryForList(PhoneQueries.GET_COLOR_IDS_FOR_PHONE, Integer.class, phone.getId());
        if (!colorIds.isEmpty()) {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("ids", colorIds);
            List<Color> colors = namedParameterJdbcTemplate
                    .query(PhoneQueries.GET_COLORS_BY_IDS, source, new BeanPropertyRowMapper<>(Color.class));
            phone.setColors(new LinkedHashSet<>(colors));
        } else {
            phone.setColors(Collections.EMPTY_SET);
        }
    }


    private void insert(Phone phone) {
        Number newId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(getParameters(phone)));
        phone.setId(newId.longValue());
    }

    private void update(final Phone phone) {
        jdbcTemplate.update(PhoneQueries.UPDATE_PHONE_QUERY, getUpdateParameters(phone));
        jdbcTemplate.update(PhoneQueries.DELETE_COLORS_QUERY, phone.getId());
        insertColors(phone);
    }

    private void insertColors(final Phone phone) {
        jdbcTemplate.batchUpdate(PhoneQueries.INSERT_COLORS_QUERY, getPhone2ColorSetter(phone));
    }

    private Map<String, Object> getParameters(final Phone phone) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("brand", phone.getBrand());
        parameters.put("model", phone.getModel());
        parameters.put("price", phone.getPrice());
        parameters.put("displaySizeInches", phone.getDisplaySizeInches());
        parameters.put("weightGr", phone.getWeightGr());
        parameters.put("heightMm", phone.getHeightMm());
        parameters.put("lengthMm", phone.getLengthMm());
        parameters.put("widthMm", phone.getWidthMm());
        parameters.put("announced", phone.getAnnounced());
        parameters.put("deviceType", phone.getDeviceType());
        parameters.put("os", phone.getOs());
        parameters.put("displayResolution", phone.getDisplayResolution());
        parameters.put("pixelDensity", phone.getPixelDensity());
        parameters.put("displayTechnology", phone.getDisplayTechnology());
        parameters.put("backCameraMegapixels", phone.getBackCameraMegapixels());
        parameters.put("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        parameters.put("ramGb", phone.getRamGb());
        parameters.put("internalStorageGb", phone.getInternalStorageGb());
        parameters.put("batteryCapacityMah", phone.getBatteryCapacityMah());
        parameters.put("standByTimeHours", phone.getStandByTimeHours());
        parameters.put("talkTimeHours", phone.getTalkTimeHours());
        parameters.put("bluetooth", phone.getBluetooth());
        parameters.put("positioning", phone.getPositioning());
        parameters.put("imageUrl", phone.getImageUrl());
        parameters.put("description", phone.getDescription());

        return parameters;
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

    private String getFindAllInOrderQueryString(int offset, int limit, OrderEnum order) {
        return PhoneQueries.FIND_ALL_QUERY + queryParameters(offset, limit, order);
    }

    private String queryParameters(int offset, int limit, OrderEnum order) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ORDER BY " + order.getSql());
        if (limit > 0)
            builder.append(" LIMIT " + limit);
        if(offset > 0)
            builder.append(" OFFSET " + offset);
        return builder.toString();
    }

    private String getSearchForAllInOrderQueryString(int offset, int limit, OrderEnum order) {
        return PhoneQueries.FIND_ALL_QUERY + PhoneQueries.SEARCH_PART_OF_FIND_ALL_QUERY + queryParameters(offset, limit, order);
    }

    private Set<Color> getPhoneColors(List<Phone> phoneOfDifferentColors) {
        Set<Color> colors = new HashSet<>();
        phoneOfDifferentColors.forEach(e -> {
            Color color = e.getColors().iterator().next();
            Long id = color.getId();
            String code = color.getCode();
            if(id != null && code != null)
                colors.add(new Color(color.getId(), color.getCode()));
        });
        return  colors;
    }
}
