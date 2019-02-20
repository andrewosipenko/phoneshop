package com.es.core.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Component
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private ColorDao colorDao;

    private String SQL_GET_BY_ID = "select * from phones where id = :id";

    private String SQL_SAVE_PHONE = "insert into phones (brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm," +
            "announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels," +
            "ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) " +
            "values (:brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm," +
            ":announced, :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, :backCameraMegapixels, :frontCameraMegapixels," +
            ":ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";

    public Optional<Phone> get(final Long key) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", key);
        Phone phone = (Phone) namedParameterJdbcTemplate.queryForObject(SQL_GET_BY_ID, namedParameters, new BeanPropertyRowMapper(Phone.class));
        phone.setColors(colorDao.get(key));
        return Optional.of(phone);
    }

    public void save(final Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phone);
        int status = namedParameterJdbcTemplate.update(SQL_SAVE_PHONE, namedParameters, keyHolder);
        if (status != 0) {
            phone.setId(keyHolder.getKey().longValue());
            colorDao.save(phone.getColors(), keyHolder.getKey().longValue());
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
        for (Phone phone : phones) {
            phone.setColors(colorDao.get(phone.getId()));
        }
        return phones;
    }
}
