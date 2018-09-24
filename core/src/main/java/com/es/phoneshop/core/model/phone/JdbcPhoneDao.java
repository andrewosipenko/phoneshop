package com.es.phoneshop.core.model.phone;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String SQL_GET_QUERY = "select * from phones where id = ? and price is not null";
    private static final String SQL_GETCOLORS_QUERY = "select colorId, colors.code from phone2color join colors on colors.id = phone2color.colorId where phoneId = ?";
    private static final String SQL_SAVE_QUERY = "insert into phones values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SAVECOLORS_QUERY = "insert into phone2color values (?, ?)";
    private static final String SQL_COUNT_QUERY = "select count(*) from phones left join stocks on phones.id = stocks.phoneId where stocks.stock > 0 and price is not null";
    private static final String SQL_CHECKSTOCK_QUERY = "select stocks.stock from stocks where phoneId = ?";
    private static final String SQL_FINDALL_QUERY = "select * from phones left join stocks on phones.id = stocks.phoneId where stocks.stock > 0 and price is not null order by %s limit ? offset ?";
    private static final String SQL_SEARCH_QUERY = "select * from phones left join stocks on phones.id = stocks.phoneId where stocks.stock > 0 and price is not null and upper(phones.model) like upper(?) order by %s limit ? offset ?";
    private static final String SQL_SEARCHCOUNT_QUERY = "select count(*) from phones left join stocks on phones.id = stocks.phoneId where stocks.stock > 0 and price is not null and upper(phones.model) like upper(?)";
    private static final String SQL_DECREASESTOCK_QUERY = "update stocks set stock = stock - %d where phoneId=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Phone> get(final Long key) {
       try {
           Phone phone = (Phone) jdbcTemplate.queryForObject(SQL_GET_QUERY, new Object[]{key}, new BeanPropertyRowMapper(Phone.class));
           return Optional.of(phone);
       }
       catch(EmptyResultDataAccessException e) {
           return Optional.empty();
       }
    }

    @Transactional
    public void save(final Phone phone) {
        jdbcTemplate.update(SQL_SAVE_QUERY, new Object[]{phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
        phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(),
        phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
        phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(),
        phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription()});
    }

    @Override
    public List<Phone> findAll(String order, int offset, int limit) {
        String orderedQuery = String.format(SQL_FINDALL_QUERY, order);
        return jdbcTemplate.query(orderedQuery, new Object[]{limit, offset}, new BeanPropertyRowMapper(Phone.class));
    }

    @Override
    public List<Phone> search(String like, String order, int offset, int limit){
        String sqlLike = "%%" + like + "%%";
        String orderedQuery = String.format(SQL_SEARCH_QUERY, order);
        return jdbcTemplate.query(orderedQuery, new Object[]{sqlLike, limit, offset}, new BeanPropertyRowMapper(Phone.class));
    }

    @Override
    public List<Color> getPhoneColors(final Long key){
        return jdbcTemplate.query(SQL_GETCOLORS_QUERY, new Object[]{key}, (ResultSet rs, int rowNumber) -> {
                Color color = new Color();
                color.setId(rs.getLong(1));
                color.setCode(rs.getString(2));
                return color;
        });
    }

    @Transactional
    public void savePhoneColors(final Long key, final Set<Color> colorSet){
        Iterator<Color> iter = colorSet.iterator();
        while(iter.hasNext()){
            jdbcTemplate.update(SQL_SAVECOLORS_QUERY, new Object[]{key, iter.next().getId()});
        }
    }

    @Override
    public int phoneCount(){
        return jdbcTemplate.queryForObject(SQL_COUNT_QUERY, Integer.class);
    }

    @Override
    public int searchCount(String like){
        return jdbcTemplate.queryForObject(SQL_SEARCHCOUNT_QUERY, new Object[]{"%" + like + "%"}, Integer.class);
    }

    @Override
    public Optional<Stock> getStock(final Long key){
        try{
            Stock stock = jdbcTemplate.queryForObject(SQL_CHECKSTOCK_QUERY, new Object[]{key}, new BeanPropertyRowMapper<>(Stock.class));
            return Optional.of(stock);
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void decreaseStock(final Long phoneId, final Long quantity) {
        String quantityQuery = String.format(SQL_DECREASESTOCK_QUERY, quantity);
        jdbcTemplate.update(quantityQuery, new Object[]{phoneId});
    }


}
