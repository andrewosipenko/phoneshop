package com.es.core.model;

import com.es.core.model.mappers.ProductRowMapper;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private static final String SELECT_PHONE_BY_ID = "SELECT id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM phones WHERE phones.id=?";

    private static final String SELECT_ALL_PHONES = "SELECT id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, " +
            "positioning, imageUrl, description FROM phones";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ProductRowMapper productRowMapper;

    @Override
    public List<Phone> getAllPhones() {
        return jdbcTemplate.query(SELECT_ALL_PHONES, productRowMapper);
    }

    @Override
    public Phone loadPhoneById(long id) {
        return jdbcTemplate.queryForObject(SELECT_PHONE_BY_ID, new Object[]{ id }, productRowMapper);
    }
}
