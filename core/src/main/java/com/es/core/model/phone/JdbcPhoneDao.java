package com.es.core.model.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        List<Integer> colorIds =
                jdbcTemplate.queryForList("select colorId from phone2color where phoneId=?",
                        new Object[]{key},
                        Integer.class);

        Set<Color> colorSet = new HashSet<>();
        for (Integer colorId : colorIds) {
            Optional<Color> color = jdbcTemplate.query("select * from colors where id=?",
                    new Object[]{colorId},
                    new ColorRowMapper()).stream().findAny();
            color.ifPresent(colorSet::add);
        }

        Optional<Phone> phone = jdbcTemplate.query("select * from phones where id=?",
                        new Object[]{key},
                        new PhoneRowMapper())
                .stream().findAny();
        phone.ifPresent(value -> value.setColors(colorSet));
        return phone;
    }

    public void save(final Phone phone) {
        Object[] phoneFields = new Object[]{
                phone.getBrand(),
                phone.getModel(),
                phone.getPrice(),
                phone.getDisplaySizeInches(),
                phone.getWeightGr(),
                phone.getLengthMm(),
                phone.getWidthMm(),
                phone.getHeightMm(),
                phone.getAnnounced(),
                phone.getDeviceType(),
                phone.getOs(),
                phone.getDisplayResolution(),
                phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(),
                phone.getRamGb(),
                phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(),
                phone.getStandByTimeHours(),
                phone.getBluetooth(),
                phone.getPositioning(),
                phone.getImageUrl(),
                phone.getDescription(),
        };
        jdbcTemplate.update("insert into phones values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", phoneFields);
        Long lastIndex = jdbcTemplate.queryForObject("select max(id) from phone", Long.class);
        for (Color color : phone.getColors()) {
            jdbcTemplate.update("insert into phone2color values (?, ?)", new Object[]{lastIndex, color.getId()});
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new PhoneRowMapper());
    }
}
