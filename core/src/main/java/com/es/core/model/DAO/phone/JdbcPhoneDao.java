package com.es.core.model.DAO.phone;

import com.es.core.model.DAO.CommonJdbcDaoUtils;
import com.es.core.model.entity.phone.Color;
import com.es.core.model.entity.phone.Phone;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.es.core.model.DAO.phone.PhoneFieldsConstantsController.*;

@Repository
public class JdbcPhoneDao implements PhoneDao {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcDaoUtils commonJdbcDaoUtils;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.commonJdbcDaoUtils = new CommonJdbcDaoUtils(jdbcTemplate);
    }

    private final ResultSetExtractor<List<Phone>> resultSetExtractor = JdbcTemplateMapperFactory
            .newInstance().addKeys(phone_id)
            .newResultSetExtractor(Phone.class);

    private static final String SELECT_ONE_BY_ID_SQL_QUERY = "SELECT phones.id AS id, brand, model, price, " +
            "displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, " +
            "os, displayResolution, pixelDensity, displayTechnology,backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description, " +
            "colors.id AS colors_id, " +
            "colors.code AS colors_code " +
            "FROM phones " +
            "LEFT JOIN phone2color ON phones.id = phone2color.phoneId " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId " +
            "WHERE phones.id = ?";

    private static final String SELECT_ALL_WITH_LIMIT_SQL_QUERY = "SELECT phonesWithColor.id AS id, brand, " +
            "model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, " +
            "announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, " +
            "backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description, " +
            "colors.id AS colors_id, colors.code AS colors_code FROM " +
            "(SELECT * FROM phones " +
            "WHERE phones.id NOT IN " +
            "(SELECT phones.id FROM phones " +
            "LEFT JOIN phone2color ON phones.id = phone2color.phoneId " +
            "WHERE phone2color.phoneId IS NULL) " +
            "LIMIT ?, ?) " +
            "AS phonesWithColor " +
            "JOIN phone2color ON phonesWithColor.id = phone2color.phoneId " +
            "JOIN colors ON colors.id = phone2color.colorId " +
            "ORDER BY phonesWithColor.id ";

    private static final String UPDATE_PHONE_SQL_QUERY = "UPDATE phones SET brand=:brand, model=:model, price=:price, " +
            "displaySizeInches=:displaySizeInches, weightGr=:weightGr, lengthMm=:lengthMm, widthMm=:widthMm, " +
            "heightMm=:heightMm, announced=:announced, deviceType=:deviceType, os=:os, displayResolution=:displayResolution, " +
            "pixelDensity=:pixelDensity, displayTechnology=:displayTechnology, backCameraMegapixels=:backCameraMegapixels, " +
            "frontCameraMegapixels=:frontCameraMegapixels, ramGb=:ramGb, internalStorageGb=:internalStorageGb, " +
            "batteryCapacityMah=:batteryCapacityMah, talkTimeHours=:talkTimeHours, standByTimeHours=:standByTimeHours, " +
            "bluetooth=:bluetooth, positioning=:positioning, imageUrl=:imageUrl, description=:description " +
            "WHERE brand=:brand AND model=:model";

    private final static String DELETE_PHONE2COLOR_RECORDS_SQL_QUERY = "DELETE FROM phone2color WHERE phoneId = ?";


    private static final String PHONES_TABLE_NAME = "phones";
    private static final String COLORS_TABLE_NAME = "colors";
    private static final String PHONE2COLORS_TABLE_NAME = "phone2color";

    private final ResultSetExtractor<List<Phone>> customPhoneMapper = (rs) -> {
        List<Phone> phones = new ArrayList<>();
        Phone currentPhone = null;
        while (rs.next()) {
            long id = rs.getLong(phone_id);
            if (currentPhone == null) {
                currentPhone = mapPhone(rs);
            } else if (currentPhone.getId() != id) {
                phones.add(currentPhone);
                currentPhone = mapPhone(rs);
            }
            currentPhone.getColors().add(mapColor(rs));
        }
        if (currentPhone != null) {
            phones.add(currentPhone);
        }
        return phones;
    };

    private final ResultSetExtractor<List<Phone>> customPhoneMapper2 = (rs) -> {
        Map<Long, Phone> phones = new LinkedHashMap<>();
        Phone currentPhone = null;
        while (rs.next()) {
            long id = rs.getLong(phone_id);
            currentPhone = mapPhone(rs);
            phones.putIfAbsent(id, currentPhone);
            phones.get(id).getColors().add(mapColor(rs)); //immutable list :(
        }
        if (currentPhone != null) {
            phones.putIfAbsent(currentPhone.getId(), currentPhone);
            phones.get(currentPhone.getId()).getColors().add(mapColor(rs));
        }
        return new ArrayList<>(phones.values());
    };

    private Phone mapPhone(ResultSet rs) throws SQLException {
        Phone phone = new Phone();
        phone.setAnnounced(rs.getDate(announced));
        phone.setBackCameraMegapixels(rs.getBigDecimal(backCameraMegapixels));
        phone.setBatteryCapacityMah(rs.getInt(batteryCapacityMah));
        phone.setBluetooth(rs.getString(bluetooth));
        phone.setBrand(rs.getString(brand_unique));
        phone.setDescription(rs.getString(description));
        phone.setDeviceType(rs.getString(deviceType));
        phone.setDisplayResolution(rs.getString(displayResolution));
        phone.setDisplaySizeInches(rs.getBigDecimal(displaySizeInches));
        phone.setDisplayTechnology(rs.getString(displayTechnology));
        phone.setFrontCameraMegapixels(rs.getBigDecimal(frontCameraMegapixels));
        phone.setHeightMm(rs.getBigDecimal(heightMm));
        phone.setId(rs.getLong(phone_id));
        phone.setImageUrl(rs.getString(imageUrl));
        phone.setInternalStorageGb(rs.getBigDecimal(internalStorageGb));
        phone.setLengthMm(rs.getBigDecimal(lengthMm));
        phone.setModel(rs.getString(model_unique));
        phone.setOs(rs.getString(os));
        phone.setPixelDensity(rs.getInt(pixelDensity));
        phone.setPrice(rs.getBigDecimal(price));
        phone.setPositioning(rs.getString(positioning));
        phone.setRamGb(rs.getBigDecimal(ramGb));
        phone.setStandByTimeHours(rs.getBigDecimal(standByTimeHours));
        phone.setTalkTimeHours(rs.getBigDecimal(talkTimeHours));
        phone.setWeightGr(rs.getInt(weightGr));
        phone.setWidthMm(rs.getBigDecimal(widthMm));
        return phone;
    }

    private Color mapColor(ResultSet rs) throws SQLException {
        var color = new Color();
        color.setId(rs.getLong("colors_id"));
        color.setCode(rs.getString("colors_code"));
        return color;
    }

    @Transactional(readOnly = true)
    public Optional<Phone> get(final Long key) {
        try {
            return jdbcTemplate.query(SELECT_ONE_BY_ID_SQL_QUERY, resultSetExtractor, key).stream().findFirst();
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public void save(final Phone phone) {
        if (commonJdbcDaoUtils.isEntityExist(
                PHONES_TABLE_NAME,
                Map.of(brand_unique, phone.getBrand(), model_unique, phone.getModel()))) {
            update(phone);
        } else {
            insert(phone);
        }
    }

    @Transactional(readOnly = true)
    public List<Phone> findAll(int offset, int limit) {
        try {
            return jdbcTemplate.query(SELECT_ALL_WITH_LIMIT_SQL_QUERY, resultSetExtractor, offset, limit);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    private void update(final Phone phone) {
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        parameterJdbcTemplate.update(UPDATE_PHONE_SQL_QUERY, new BeanPropertySqlParameterSource(phone));
        refreshBindedColors(phone);
    }

    private void insert(Phone phone) {
        Long newId = commonJdbcDaoUtils.insertAndReturnGeneratedKey(PHONES_TABLE_NAME, new BeanPropertySqlParameterSource(phone),
                phone_id).longValue();
        phone.setId(newId);
        saveColors(phone);
    }

    private void refreshBindedColors(final Phone phone) {
        jdbcTemplate.update(DELETE_PHONE2COLOR_RECORDS_SQL_QUERY, phone.getId());
        saveColors(phone);
    }

    private void saveColors(final Phone phone) {
        for (var color : phone.getColors()) {
            var simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            simpleJdbcInsert.withTableName(PHONE2COLORS_TABLE_NAME)
                    .execute(new MapSqlParameterSource()
                            .addValue("phoneId", phone.getId())
                            .addValue("colorId", color.getId()));
        }
    }
}
