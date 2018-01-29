package com.es.core.model.phone;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao{
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertPhone;

    @Resource
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertPhone = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Phone> get(final Long key) {
        try {
            Phone queriedPhone =  (Phone)jdbcTemplate.queryForObject("select * from phones where id= " + key, new BeanPropertyRowMapper(Phone.class));
            String colorsQuery = "select * from colors inner join phone2color\n" +
                    "on colors.id = phone2color.colorId\n" +
                    "where phone2color.phoneId = " + key;
            List<Color> colorsList = jdbcTemplate.query(colorsQuery,new BeanPropertyRowMapper(Color.class));
            Set<Color> colorsSet = new HashSet<>(colorsList);
            queriedPhone.setColors(colorsSet);
            return Optional.of(queriedPhone);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        if(phone.getId() == null) {
            insert(phone);
        }
        else{
            update(phone);
        }
    }

    private void insert(final Phone phone){
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(phone);
        final long newId = insertPhone.executeAndReturnKey(parameters).longValue();
        phone.setId(newId);
        insertColors(phone);
    }

    private void update(final Phone phone){
        jdbcTemplate.update(
                "update phones set brand = ? " +
                        ",model = ? " +
                        ",price = ? " +
                        ",displaySizeInches = ? " +
                        ",weightGr = ? " +
                        ",lengthMm = ? " +
                        ",widthMm = ? " +
                        ",heightMm = ? " +
                        ",announced = ? " +
                        ",deviceType = ? " +
                        ",os = ? " +
                        ",displayResolution = ? " +
                        ",pixelDensity = ? " +
                        ",displayTechnology = ? " +
                        ",backCameraMegapixels = ? " +
                        ",frontCameraMegapixels = ?" +
                        ",ramGb = ? " +
                        ",internalStorageGb = ? " +
                        ",batteryCapacityMah = ? " +
                        ",talkTimeHours = ? " +
                        ",standByTimeHours = ? " +
                        ",bluetooth = ? " +
                        ",positioning = ? " +
                        ",imageUrl = ? " +
                        ",description = ? " +
                        "where id = ? ",
                phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription(), phone.getId());

        jdbcTemplate.update("delete from phone2color where phoneId = " + phone.getId());
        insertColors(phone);
    }

    private void insertColors(final Phone phone){
        jdbcTemplate.batchUpdate("insert into phone2color (phoneId,colorId) values (?,?) ",
                new BatchPreparedStatementSetter() {

                    List<Color> colors = new ArrayList<>(phone.getColors());

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1,phone.getId());
                        ps.setLong(2,colors.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return colors.size();
                    }
                });
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }
}
