package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone = Optional.ofNullable(jdbcTemplate
                .queryForObject("select * from phones where id = ?", new Object[]{key},
                        BeanPropertyRowMapper.newInstance(Phone.class)));
        if (phone.isPresent()) {
            List<Long> colorIds = jdbcTemplate.queryForList("select colorId from phone2color where phoneId = ?",
                    new Object[]{key}, Long.class);
            Set<Color> colors = colorIds.stream()
                    .map(colorId -> jdbcTemplate.queryForObject("select * from colors where id = ?",
                            new Object[]{colorId}, BeanPropertyRowMapper.newInstance(Color.class)))
                    .collect(Collectors.toSet());
            phone.get().setColors(colors);
        }
        return phone;
    }

    public void save(final Phone phone) {
        jdbcTemplate.update("insert into phones values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                        " ?, ?, ?, ?, ?, ?)",
                phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription());
        phone.getColors().forEach(color -> jdbcTemplate.update("insert into phone2color values (?, ?)",
                phone.getId(), color.getId()));
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }
}
