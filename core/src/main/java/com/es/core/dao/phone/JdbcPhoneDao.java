package com.es.core.dao.phone;

import com.es.core.dao.color.ColorDao;
import com.es.core.model.phone.Phone;
import com.es.core.setExtractor.PhoneSetExtractor;
import com.es.core.setExtractor.PhonesSetExtractor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPhoneDao implements PhoneDao {
    private final static String REPLACE_REGEX = "var";
    private final static String QUERY_FOR_FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT =
            "select * from phones offset ? limit ?";
    private final static String QUERY_FOR_FIND_PHONE_BY_ID =
            "select phones.*, c.id as colorId, c.code from " +
                    "(select * from phones where id = ?) phones " +
                    "left join colors c on c.id in " +
                    "(select colorId from phone2color where phones.id = phone2color.phoneId)";
    private final static String QUERY_TO_SAVE_PHONE =
            "merge into phones (brand, model, price, displaySizeInches, weightGr, lengthMm, " +
                    "widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
                    "displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, " +
                    "batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
                    "description) key(brand, model) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String QUERY_TO_FIND_PHONE_ID =
            "select id from phones where brand = ? and model = ?";
    private final static String QUERY_TO_GET_ACTIVE_PHONES_BY_PAGE =
            "select phones.*, c.id as colorId, c.code from " +
                    "(select * from phones where id in " +
                    "(select phoneid from stocks where stock > '0') offset ? limit ?) phones " +
                    "left join colors c on c.id in " +
                    "(select colorId from phone2color where phones.id = phone2color.phoneId)";
    private final static String QUERY_TO_GET_SORT_PHONES_BY_PAGE =
            "select phones.*, c.id as colorId, c.code from " +
                    "(select * from phones where id in " +
                    "(select phoneid from stocks where stock > '0') order by var var offset ? limit ?) phones " +
                    "left join colors c on c.id in " +
                    "(select colorId from phone2color where phones.id = phone2color.phoneId)";
    private final static String QUERY_TO_GET_PHONES_LIKE_QUERY_BY_PAGE =
            "select phones.*, c.id as colorId, c.code from " +
                    "(select * from phones where id in " +
                    "(select phoneid from stocks where stock > '0') " +
                    "and (model like ? or brand like ?) offset ? limit ?) phones " +
                    "left join colors c on c.id in " +
                    "(select colorId from phone2color where phones.id = phone2color.phoneId)";
    private final static String QUERY_TO_GET_SORT_PHONES_LIKE_QUERY_BY_PAGE =
            "select phones.*, c.id as colorId, c.code from " +
                    "(select * from phones where id in " +
                    "(select phoneid from stocks where stock > '0') " +
                    "and (model like ? or brand like ?) order by var var offset ? limit ?) phones " +
                    "left join colors c on c.id in " +
                    "(select colorId from phone2color where phones.id = phone2color.phoneId)";
    private final static String QUERY_FOR_GET_ACTIVE_PHONE_COUNT =
                "select count(1) from phones where id in (" +
                        "select phoneId from stocks where stock > '0')";
    private final static String QUERY_FOR_GET_PHONE_LIKE_QUERY_COUNT =
            "select count(*) from phones where id in (" +
                    "select phoneId from stocks where stock > '0') " +
                    "and (model like ? or brand like ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ColorDao colorDao;

    public Optional<Phone> get(Long key) {
        Optional<Phone> optionalPhone;
        try {
            Phone phone = jdbcTemplate
                    .query(QUERY_FOR_FIND_PHONE_BY_ID, new PhoneSetExtractor(), key);
            optionalPhone = Optional.of(phone);
        } catch (EmptyResultDataAccessException e) {
            optionalPhone = Optional.empty();
        }
        return optionalPhone;
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(QUERY_FOR_FIND_ALL_PHONES_WITH_OFFSET_AND_LIMIT,
                new BeanPropertyRowMapper(Phone.class), offset, limit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Phone phone) {
        jdbcTemplate.update(QUERY_TO_SAVE_PHONE,
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
                phone.getDescription()
        );

        if (phone.getId() == null) {
            Long id = jdbcTemplate.queryForObject(QUERY_TO_FIND_PHONE_ID, Long.class, phone.getBrand(), phone.getModel());
            phone.setId(id);
        }

        colorDao.savePhoneColors(phone);
    }

    @Override
    public List<Phone> findPhonesLikeQuery(int offset, int limit, String query) {
        return jdbcTemplate.query(QUERY_TO_GET_PHONES_LIKE_QUERY_BY_PAGE, new PhonesSetExtractor(),
                query, query, offset, limit);
    }

    @Override
    public List<Phone> sortPhones(int offset, int limit, String sort, String order) {
        String dbQeury = QUERY_TO_GET_SORT_PHONES_BY_PAGE.replaceFirst(REPLACE_REGEX, sort);
        dbQeury = dbQeury.replaceFirst(REPLACE_REGEX, order);
        return jdbcTemplate.query(dbQeury, new PhonesSetExtractor(), offset, limit);
    }

    @Override
    public List<Phone> sortPhonesLikeQuery(int offset, int limit, String sort, String order, String query) {
        String dbQeury = QUERY_TO_GET_SORT_PHONES_LIKE_QUERY_BY_PAGE.replaceFirst(REPLACE_REGEX, sort);
        dbQeury = dbQeury.replaceFirst(REPLACE_REGEX, order);
        return jdbcTemplate.query(dbQeury, new PhonesSetExtractor(),
                query, query, offset, limit);
    }

    @Override
    public int findPageCount(int pageSize) {
        return jdbcTemplate.queryForObject(QUERY_FOR_GET_ACTIVE_PHONE_COUNT, Integer.class) / pageSize;
    }

    @Override
    public List<Phone> findActivePhonesByPage(int offset, int limit) {
        return jdbcTemplate.query(QUERY_TO_GET_ACTIVE_PHONES_BY_PAGE,
                new PhonesSetExtractor(), offset, limit);
    }

    @Override
    public int findPageCountWithQuery(int pageSize, String query) {
        return jdbcTemplate.queryForObject(QUERY_FOR_GET_PHONE_LIKE_QUERY_COUNT,
                Integer.class, query, query) / pageSize;
    }
}