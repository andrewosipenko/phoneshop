package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    private final static String FIND_ALL_SQL_QUERY =
            "SELECT p.id, p.imageUrl, p.brand, p.model, c.code as colorCode, c.id as colorId, p.displaySizeInches, p.price " +
            "FROM (SELECT * " +
            "      FROM phones, stocks s " +
            "      WHERE phones.id=s.phoneId and s.stock>0 " +
            "      LIMIT ? OFFSET ?) p " +
            "LEFT JOIN phone2color p2c ON p2c.phoneid = p.id " +
            "LEFT JOIN colors c ON c.id = p2c.colorId " +
            "ORDER BY p.id";

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

    @Override
    public List<Phone> findAll(int offset, int limit){
        return jdbcTemplate.query(
                FIND_ALL_SQL_QUERY,
                new FindAllResultSetExtractor(),
                limit,
                offset
        );
    }

    private static class FindAllResultSetExtractor implements ResultSetExtractor<List<Phone>>{

        @Override
        public List<Phone> extractData(ResultSet rs) throws SQLException, DataAccessException {
            LinkedList<Phone> phones = new LinkedList<>();
            if(rs.next()){
                phones.add(getPhone(rs));
            }

            Long phoneId;
            Phone lastPhone;
            while(rs.next()){
                lastPhone = phones.getLast();
                phoneId = rs.getLong("id");
                if (lastPhone.getId().equals(phoneId)) {
                    lastPhone.getColors().add(getColor(rs));
                } else {
                    phones.add(getPhone(rs));
                }
            }
            return phones;
        }

        private Phone getPhone(ResultSet rs) throws SQLException, DataAccessException{
            Phone phone = new Phone();
            Long phoneId = rs.getLong("id");
            phone.setId(phoneId);
            phone.setImageUrl(rs.getString("imageUrl"));
            phone.setBrand(rs.getString("brand"));
            phone.setModel(rs.getString("model"));
            phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
            phone.setPrice(rs.getBigDecimal("price"));

            Set<Color> colors = new HashSet<>();
            colors.add(getColor(rs));
            phone.setColors(colors);
            return phone;
        }

        private Color getColor(ResultSet rs) throws SQLException, DataAccessException{
            Color color = new Color();
            color.setCode(rs.getString("colorCode"));
            color.setId(rs.getLong("colorId"));
            return color;
        }
    }

//    public List<Phone> findAll(int offset, int limit) {
//        return jdbcTemplate.query(
//                FIND_ALL_SQL_QUERY,
//                new BeanPropertyRowMapper(Phone.class),
//                offset,
//                limit
//        );
//    }
}
