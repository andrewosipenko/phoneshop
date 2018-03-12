package com.es.core.model.phone;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertPhone;

    @Resource
    public void setDataSource(DataSource dataSource){
        insertPhone = new SimpleJdbcInsert(dataSource).withTableName("phones").usingGeneratedKeyColumns("id");
    }

    public Optional<Phone> get(final Long key) {
        try{
            Phone phone = (Phone)jdbcTemplate.queryForObject("select * from phones where phones.id = " + key,
                    new BeanPropertyRowMapper(Phone.class));
            setColor(phone);
            return Optional.of(phone);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        long amount = jdbcTemplate.queryForObject("select count(*) from phones where phones.id = " + phone.getId(),
                Long.class);
        if (amount == 0) {
            insertPhone(phone);
        }
        else {
            updatePhone(phone);
        }
    }

    private void insertPhone(final Phone phone){
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(phone);
        Long phoneId = insertPhone.executeAndReturnKey(parameters).longValue();
        phone.setId(phoneId);
        insertColors(phone);
    }

    private void insertColors(final Phone phone){
        jdbcTemplate.batchUpdate("insert into phone2color (phoneId, colorId) values (?, ?)", new BatchPreparedStatementSetter() {

            List<Color> colors = new ArrayList<>(phone.getColors());

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException
            {
                preparedStatement.setLong(1, phone.getId());
                preparedStatement.setLong(2, colors.get(i).getId());
            }

            @Override
            public int getBatchSize()
            {
                return colors.size();
            }
        });
    }

    private void updatePhone(final Phone phone) {
        jdbcTemplate.update(SqlQueryConstants.SQL_QUERY_PHONE_UPDATE + phone.getId(),
                phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(),
                phone.getImageUrl(), phone.getDescription());
        updateColors(phone);
    }

    private void updateColors(final Phone phone) {
        jdbcTemplate.update("delete from phone2color where phone2color.phoneId = " + phone.getId());
        insertColors(phone);
    }


    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit,
                new BeanPropertyRowMapper(Phone.class));
        for (Phone phone : phones) {
            setColor(phone);
        }
        return phones;
    }

    private void setColor(final Phone phone) {
        Long phoneId = phone.getId();
        List<Color> colors = jdbcTemplate.query("select * from colors inner join phone2color " +
                "on colors.id = phone2color.colorId where phone2color.phoneId = " + phoneId,
                new BeanPropertyRowMapper(Color.class));
        phone.setColors(new HashSet<>(colors));
    }
}