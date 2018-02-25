package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_GET_PHONE_QUERY = "SELECT * FROM phones WHERE id=?";

    private final static String SQL_GET_COLORS_QUERY =
            "SELECT colors.id, colors.code " +
            "FROM colors, phone2color " +
            "WHERE phone2color.colorId = colors.id AND phone2color.phoneId=?";

    private final static String SQL_INSERT_PHONE_IF_NOT_EXIST_QUERY =
            "INSERT INTO phones (id, brand, model) " +
            "SELECT ?, ?, ? " +
            "WHERE 0 = (SELECT COUNT(*) FROM phones WHERE id=?)";

    private final static String SQL_UPDATE_PHONE_QUERY =
            "UPDATE phones " +
            "SET brand=?, " +
            "model=?, " +
            "price=?, " +
            "displaySizeInches=?, " +
            "weightGr=?, " +
            "lengthMm=?, " +
            "widthMm=?, " +
            "heightMm=?, " +
            "announced=?, " +
            "deviceType=?, " +
            "os=?, " +
            "displayResolution=?, " +
            "pixelDensity=?, " +
            "displayTechnology=?, " +
            "backCameraMegapixels=?, " +
            "frontCameraMegapixels=?, " +
            "ramGb=?, " +
            "internalStorageGb=?, " +
            "batteryCapacityMah=?, " +
            "talkTimeHours=?, " +
            "standByTimeHours=?, " +
            "bluetooth=?, " +
            "positioning=?, " +
            "imageUrl=?, " +
            "description=? " +
            "WHERE id=?";

    public Optional<Phone> get(final Long key) {
        List<Phone> result = queryPhone(key);

        Optional<Phone> phoneOptional;
        if(result.size() == 0){
            phoneOptional = Optional.empty();
        } else {
            Phone phone = result.get(0);
            phone.setColors(queryColors(key));
            phoneOptional = Optional.of(result.get(0));
        }

        return phoneOptional;
    }

    private List<Phone> queryPhone(final Long key) {
        return jdbcTemplate.query(
                SQL_GET_PHONE_QUERY,
                new BeanPropertyRowMapper<>(Phone.class),
                key
        );
    }

    private Set<Color> queryColors(final Long key) {
        return new HashSet<>(jdbcTemplate.query(
                SQL_GET_COLORS_QUERY,
                (resultSet, i) -> {
                    Color color = new Color();
                    color.setCode(resultSet.getString("code"));
                    color.setId(resultSet.getLong("id"));
                    return color;
                },
                key
        ));
    }

    public void save(final Phone phone) {
        jdbcTemplate.update(SQL_INSERT_PHONE_IF_NOT_EXIST_QUERY,
                phone.getId(),
                phone.getBrand(),
                phone.getModel(),
                phone.getId()
        );

        jdbcTemplate.update(SQL_UPDATE_PHONE_QUERY,
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
                phone.getPixelDensity(),
                phone.getDisplayTechnology(),
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
                phone.getId()
        );
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
    }
}
