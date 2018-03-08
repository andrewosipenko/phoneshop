package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
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

    private final static String SQL_COUNT_SEARCH_QUERY =
            "SELECT COUNT(*) " +
            "FROM phones, stocks s " +
            "WHERE phones.id=s.phoneId and s.stock>0 " +
            "      and phones.model LIKE ? ";

    private final static String SQL_PHONES_QUERY =
            "SELECT p.id, p.imageUrl, p.brand, p.model, c.code as colorCode, c.id as colorId, p.displaySizeInches, p.price " +
            "FROM (SELECT * " +
            "      FROM phones, stocks s " +
            "      WHERE phones.id=s.phoneId and s.stock>0 " +
            "            and phones.model LIKE ? " +
            "      ORDER BY %s " +
            "      LIMIT ? OFFSET ?) p " +
            "LEFT JOIN phone2color p2c ON p2c.phoneId = p.id " +
            "LEFT JOIN colors c ON c.id = p2c.colorId ";


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
                (ResultSet resultSet, int i) -> {
                    Color color = new Color();
                    color.setCode(resultSet.getString("code"));
                    color.setId(resultSet.getLong("id"));
                    return color;
                },
                key
        ));
    }

    private List<Phone> queryPhones(String keyString, int limit, int offset, String sortBy){
        keyString = String.format("%%%s%%", keyString);
        return jdbcTemplate.query(
                String.format(SQL_PHONES_QUERY, sortBy),
                new FindAllPhonesResultSetExtractor(),
                keyString,
                limit,
                offset
        );
    }

    private void insertPhone(final Phone phone){
        jdbcTemplate.update(SQL_INSERT_PHONE_IF_NOT_EXIST_QUERY,
                phone.getId(),
                phone.getBrand(),
                phone.getModel(),
                phone.getId()
        );
    }

    private void updatePhone(final Phone phone){
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

    private String getSortByParam(String sortBy) {
        switch (sortBy) {
            case "brand":
                return "phones.brand";
            case "model":
                return "phones.model";
            case "display":
                return "phones.displaySizeInches";
            case "price":
                return "phones.price";
            default:
                return "phones.id";
        }
    }

    private int countPhones(String keyString){
        return jdbcTemplate.queryForObject(
                SQL_COUNT_SEARCH_QUERY,
                Integer.class,
                String.format("'%%%s%%'", keyString)
        );
    }

    @Override
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

    @Override
    public void save(final Phone phone) {
        insertPhone(phone);
        updatePhone(phone);
    }

    @Override
    public List<Phone> findAll(int offset, int limit, String sortBy){
        sortBy = getSortByParam(sortBy);
        return queryPhones("", limit, offset, sortBy);
    }

    @Override
    public List<Phone> searchByModel(String keyString, int limit, int offset, String sortBy){
        sortBy = getSortByParam(sortBy);
        return queryPhones(keyString, limit, offset, sortBy);
    }

    @Override
    public int countSearchResult(String keyString){
        return countPhones(keyString);
    }

    @Override
    public int countAll(){
        return countPhones("");
    }
}
