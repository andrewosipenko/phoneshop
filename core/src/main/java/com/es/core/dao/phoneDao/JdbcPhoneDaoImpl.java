package com.es.core.dao.phoneDao;

import com.es.core.model.SqlOrderByKeyword;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.h2.util.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDaoImpl implements PhoneDao {

    private static final String GET_ALL_PHONES_WITH_LIMIT_OFFSET_QUERY = "SELECT * FROM phones " +
            "INNER JOIN stocks ON phones.id = stocks.phoneId WHERE stocks.stock > 0 AND phones.price IS NOT NULL OFFSET ? LIMIT ?";
    private static final String GET_ALL_PHONES_QUERY = "SELECT * FROM phones " +
            "INNER JOIN stocks ON phones.id = stocks.phoneId WHERE stocks.stock > 0 AND phones.price IS NOT NULL ";
    private static final String GET_COLORS_BY_PHONE_KEY_QUERY = "SELECT * FROM colors " +
            "INNER JOIN phone2color ON (colors.id = phone2color.colorId AND phone2color.phoneId = ?)";
    private static final String UPDATE_PHONE_WITH_ID_QUERY = "UPDATE phones SET brand = ?, model = ?, price = ?, displaySizeInches = ?, " +
            "weightGr = ?, lengthMm = ?, widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, os = ?, displayResolution = ?, " +
            "pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, frontCameraMegapixels = ?, ramGb = ?, " +
            "internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, standByTimeHours = ?, bluetooth = ?, " +
            "positioning = ?, imageUrl = ?, description = ? WHERE id = ?";
    private static final String DELETE_FROM_PHONE_2_COLOR_WHERE_PHONE_ID = "DELETE FROM phone2color WHERE phoneId = ?";
    private static final String INSERT_INTO_PHONE_2_COLOR_PHONE_ID_COLOR_ID_BATCH_QUERY = "INSERT INTO phone2color (phoneId, colorId) VALUES (?, ?)";
    private static final String SELECT_FROM_PHONES_BY_ID_QUERY = "SELECT * FROM phones WHERE id = ?";
    private static final String GET_PHONES_COUNT_OF_PAGE = "SELECT COUNT(*) FROM phones INNER JOIN stocks ON phones.id = stocks.phoneId " +
            "WHERE stocks.stock > 0 AND phones.price IS NOT NULL ";

    @Resource
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper;

    @Resource
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper;

    @Resource
    private SimpleJdbcInsert phoneSimpleJdbcInsert;


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Phone> get(final Long key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null !!!");
        }
        Phone phone;
        try {
            phone = jdbcTemplate.queryForObject(SELECT_FROM_PHONES_BY_ID_QUERY, phoneBeanPropertyRowMapper, key);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        phone.setColors(extractColors(key));
        return Optional.of(phone);
    }


    @Override
    public void save(final Phone phone) {
        if (phone.getId() == null) {
            addPhone(phone);
        } else {
            updatePhone(phone);
        }
        colorBatchInsert(phone);
    }

    private void colorBatchInsert(Phone phone) {
        Long phoneId = phone.getId();
        List<Color> colors = new ArrayList<>(phone.getColors());
        jdbcTemplate.batchUpdate(INSERT_INTO_PHONE_2_COLOR_PHONE_ID_COLOR_ID_BATCH_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Color color = colors.get(i);
                ps.setLong(1, phoneId);
                ps.setLong(2, color.getId());
            }

            @Override
            public int getBatchSize() {
                return colors.size();
            }
        });
    }

    private void addPhone(final Phone phone) {
        BeanPropertySqlParameterSource phoneParameterSource = new BeanPropertySqlParameterSource(phone);
        Long id = phoneSimpleJdbcInsert.executeAndReturnKey(phoneParameterSource).longValue();
        phone.setId(id);
    }

    private void updatePhone(final Phone phone) {
        jdbcTemplate.update(UPDATE_PHONE_WITH_ID_QUERY, phone.getBrand(),
                phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(),
                phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(),
                phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription(),
                phone.getId());
        jdbcTemplate.update(DELETE_FROM_PHONE_2_COLOR_WHERE_PHONE_ID, phone.getId());
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(GET_ALL_PHONES_WITH_LIMIT_OFFSET_QUERY,
                phoneBeanPropertyRowMapper, offset, limit);

        phones.forEach(phone -> phone.setColors(extractColors(phone.getId())));

        return phones;
    }

    @Override
    public List<Phone> findAll(String query, String sortBy, SqlOrderByKeyword sqlOrderByKeyword, int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(generateOrderQuery(query,sortBy, sqlOrderByKeyword),
                phoneBeanPropertyRowMapper, offset, limit);
        phones.forEach(phone -> phone.setColors(extractColors(phone.getId())));
        return phones;
    }

    private String generateOrderQuery(String query, String sortBy, SqlOrderByKeyword sqlOrderByKeyword) {
        StringBuilder sb = new StringBuilder().append(GET_ALL_PHONES_QUERY);
        parseQueryAndModifyQuery(query, sb);
        sb.append("ORDER BY ").append(sortBy).append(" ").append(sqlOrderByKeyword).append(", ")
                .append("brand".equalsIgnoreCase(sortBy) ? "model " : "brand ").append(SqlOrderByKeyword.ASC)
                .append(" OFFSET ? LIMIT ?");
        return sb.toString();
    }

    @Override
    public int getTotalNumberOfProducts(String query) {
        return jdbcTemplate.queryForObject(generateQueryForCountOfPage(query), Integer.class);
    }

    private String generateQueryForCountOfPage(String query) {
        StringBuilder sb = new StringBuilder().append(GET_PHONES_COUNT_OF_PAGE);
        parseQueryAndModifyQuery(query, sb);
        return sb.toString();
    }

    private void parseQueryAndModifyQuery(String query, StringBuilder sb) {
        if (!StringUtils.isNullOrEmpty(query)) {
            List<String> queryItems = Arrays.stream(query.trim().split(" "))
                    .map(item -> "UPPER(phones.model) LIKE '%" + item.toUpperCase() + "%'")
                    .collect(Collectors.toList());

            sb.append("AND (").append(String.join(" OR ", queryItems)).append(") ");
        }
    }

    private Set<Color> extractColors(final Long key) {
        List<Color> colorList = jdbcTemplate.query(GET_COLORS_BY_PHONE_KEY_QUERY, colorBeanPropertyRowMapper, key);
        return new HashSet<>(colorList);
    }

}