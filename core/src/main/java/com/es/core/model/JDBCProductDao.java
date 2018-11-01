package com.es.core.model;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository("JDBCProductDao")
public class JDBCProductDao implements PhoneDao, InitializingBean {
    @Resource
    private JdbcTemplate phone2colorJdbcTemplate;
    private Map<Long, Color> colors;

    @Override
    public void afterPropertiesSet() {
        colors = phone2colorJdbcTemplate.query("select * from colors", new BeanPropertyRowMapper<>(Color.class)).stream()
                .collect(Collectors.toMap((c) -> (c).getId(), (c) -> c));
    }

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone = Optional.ofNullable(phone2colorJdbcTemplate.queryForObject("select * from phones where phones.id"+key, new BeanPropertyRowMapper<>(Phone.class)));
        if (phone.isPresent()) {
            setColorsForPhone(phone.get());
        }
        return phone;
    }

    public void save(final Phone phone) {
        if (phone.getId() != null) {
            delete(phone.getId());
            String sqlInsertion = "insert into phones values (" + Stream.of(phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl())
                    .map((s) -> s != null ? "String Date Long".contains(s.getClass().getSimpleName()) ? "'" + s + "', " : s + ", " : s + ", ")
                    .collect(Collectors.joining())
                    .concat(phone.getDescription() + ");");
            phone2colorJdbcTemplate.execute(sqlInsertion);
            if (!phone.getColors().equals(Collections.EMPTY_SET)) {
                for (Color color : phone.getColors()) {
                    phone2colorJdbcTemplate.update("insert into phone2color values (?,?)", phone.getId(), color.getId());
                }
            }
        } else {
            //TODO
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = phone2colorJdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
        for (Phone phone : phones) {
            setColorsForPhone(phone);
        }
        return phones;
    }

    public void delete(final Long key) {
        phone2colorJdbcTemplate.update("delete from phones where id = ?", key);
    }

    private void setColorsForPhone(Phone phone) {
        phone.setColors(new HashSet<>());
        phone2colorJdbcTemplate.query("select * from phone2color where phone2color.phoneId = "+phone.getId(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                phone.getColors().add(colors.get(resultSet.getLong("colorId")));
            }
        });
    }
}
