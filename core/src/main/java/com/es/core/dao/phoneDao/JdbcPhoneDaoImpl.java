package com.es.core.dao.phoneDao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDaoImpl implements PhoneDao {

    private static final String DELETE_FROM_PHONE2COLOR_QUERY = "delete from phone2color " +
            "where phoneId = ? and colorId = ?";

    private static final String GET_PHONE_QUERY = "select * from (select * from phones " +
            "inner join stocks s on id=s.phoneId where id=?) p " +
            "left outer join phone2color p2с on p.id=p2с.phoneId " +
            "left outer join colors c on p2с.colorId=c.id";

    private static final String FIND_ALL_QUERY = "select * from (select * from phones " +
            "inner join stocks s on id=s.phoneId where s.stock>0 offset ? limit ?) p " +
            "left outer join phone2color p2с on p.id=p2с.phoneId " +
            "left outer join colors c on p2с.colorId=c.id";
    private final static String FIND_BY_ID_QUERY = "select phones.*, colors.id, colors.code from" +
            " PUBLIC.PHONES left join phone2color on phones.id = phone2color.phoneId" +
            " left join colors on phone2colors.colorsId = colors.id where phones.id = ?";

    private static final String UPDATE_PHONES_QUERY = "update phones set brand=?,model=?,price=?,displaySizeInches=?," +
            "weightGr=?,lengthMm=?,widthMm=?,heightMm=?,announced=?,deviceType=?,os=?,displayResolution=?," +
            "pixelDensity=?,displayTechnology=?,backCameraMegapixels=?,frontCameraMegapixels=?,ramGb=?," +
            "internalStorageGb=?,batteryCapacityMah=?,talkTimeHours=?,standByTimeHours=?,bluetooth=?,positioning=?," +
            "imageUrl=?,description=? where id=?";

    private static final String INSERT_INTO_PHONES_QUERY = "insert into phones " +
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String UPDATE_STOCKS_QUERY = "update stocks set stock=? where phoneId=?";

    private static final String INSERT_INTO_STOCKS_QUERY = "insert into stocks set values (?,?)";

    private static final String GET_COLORS_QUERY = "select color.id, color.code from phone2color p2c " +
            "inner join colors c on c.id=p2c.colorId where phoneId=?";

    private static final String INSERT_INTO_PHONE2COLOR_QUERY = "insert into phone2color (phoneId, colorId) values (?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPhoneDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Phone> get(final Long key) {
        List<Phone> phoneList = jdbcTemplate.query(GET_PHONE_QUERY, new PhoneResultSetExtractor(), key);
        return phoneList.stream().findAny();
    }

    @Override
    public void save(final Phone phone) {
        Long id = phone.getId();

        updateOrSavePhone(phone, id);

        Set<Color> colorsFromPhone = phone.getColors();
        List<Color> colorsFromPhone2Color = jdbcTemplate.query(GET_COLORS_QUERY, new BeanPropertyRowMapper(Color.class), id);
        for (Color color : colorsFromPhone) {
            if (!colorsFromPhone2Color.contains(color)) {
                Long colorId = color.getId();
                jdbcTemplate.update(INSERT_INTO_PHONE2COLOR_QUERY, id, colorId);
            }
        }
        for (Color color : colorsFromPhone2Color) {
            if (!colorsFromPhone.contains(color)) {
                Long colorId = color.getId();
                jdbcTemplate.update(DELETE_FROM_PHONE2COLOR_QUERY, id, colorId);
            }
        }
    }

    private void updateOrSavePhone(Phone phone, Long id) {
        Optional<Phone> optionalPhone = get(id);
        if (optionalPhone.isPresent()) {
            addPhone(phone);
        } else {
            update(phone);
        }
    }

    private void addPhone(Phone phone) {
        jdbcTemplate.update(INSERT_INTO_PHONES_QUERY, phone.getId(), phone.getBrand(), phone.getModel(),
                phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(),
                phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(),
                phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(),
                phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(),
                phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription());
        jdbcTemplate.update(INSERT_INTO_STOCKS_QUERY, phone.getId());
    }

    @Override
    public void update(Phone phone) {
        jdbcTemplate.update(UPDATE_PHONES_QUERY, phone.getBrand(), phone.getModel(),
                phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(),
                phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(),
                phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(),
                phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(),
                phone.getPositioning(), phone.getImageUrl(), phone.getDescription(), phone.getId());
        jdbcTemplate.update(UPDATE_STOCKS_QUERY, phone.getId());
    }

    @Override
    public void delete(Phone model) {

    }


    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL_QUERY, new PhoneResultSetExtractor(), offset, limit);
    }

}
