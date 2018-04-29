package com.es.core.dao.phoneDao;

import com.es.core.dao.SqlQueryConstants;
import com.es.core.dao.phoneDao.rowMapper.StockPropertyRowMapper;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertPhone;

    @Resource
    public void setDataSource(DataSource dataSource){
        insertPhone = new SimpleJdbcInsert(dataSource).withTableName("phones").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Phone> get(final Long key) {
        try{
            Phone phone = (Phone)jdbcTemplate.queryForObject(SqlQueryConstants.PhoneDao.SELECT_PHONES_BY_ID + key,
                    new BeanPropertyRowMapper(Phone.class));
            setColor(phone);
            return Optional.of(phone);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void save(final Phone phone) {
        long amount = jdbcTemplate.queryForObject(SqlQueryConstants.PhoneDao.COUNT_PHONES_WITH_ID + phone.getId(),
                Long.class);
        if (amount == 0) {
            insertPhone(phone);
        }
        else {
            updatePhone(phone);
        }
    }

    private void insertPhone(final Phone phone){
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(phone);
        Long phoneId = insertPhone.executeAndReturnKey(parameters).longValue();
        phone.setId(phoneId);
        insertColors(phone);
    }

    private void insertColors(final Phone phone){
        jdbcTemplate.batchUpdate(SqlQueryConstants.PhoneDao.INSERT_INTO_PHONE2COLOR, new BatchPreparedStatementSetter() {

            List<Color> colors = new ArrayList<>(phone.getColors());

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException
            {
                preparedStatement.setLong(1, phone.getId());
                preparedStatement.setLong(2, colors.get(i).getId());
            }

            @Override
            public int getBatchSize()
            {
                return colors.size();
            }
        });
    }

    private void updatePhone(final Phone phone) {
        jdbcTemplate.update(SqlQueryConstants.PhoneDao.PHONE_UPDATE + phone.getId(),
                phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(),
                phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(),
                phone.getImageUrl(), phone.getDescription());
        updateColors(phone);
    }

    private void updateColors(final Phone phone) {
        jdbcTemplate.update(SqlQueryConstants.PhoneDao.DELETE_FROM_PHONE2COLOR_BY_PHONE_ID + phone.getId());
        insertColors(phone);
    }

    @Override
    public List<Phone> findAll(int offset, int limit, String search) {
        search = "%" + search.trim().toLowerCase() + "%";
        List<Phone> phones = jdbcTemplate.query(SqlQueryConstants.PhoneDao.SELECT_AVAILABLE_PHONES_WITH_OFFSET
                        + offset + " LIMIT " + limit,
                new BeanPropertyRowMapper(Phone.class), search);
        for (Phone phone : phones) {
            setColor(phone);
        }
        return phones;
    }

    @Override
    public List<Phone> findAllSortedBy(int offset, int limit, String search, String sortBy, String dir) {
        search = "%" + search.trim().toLowerCase() + "%";
        List<Phone> phones = jdbcTemplate.query(
                SqlQueryConstants.PhoneDao.SELECT_AVAILABLE_PHONES_SORTED + sortBy + " " + dir.toUpperCase()
                        + " OFFSET " + offset + " LIMIT " + limit,
                new BeanPropertyRowMapper(Phone.class), search);
        for (Phone phone : phones) {
            setColor(phone);
        }
        return phones;
    }

    private void setColor(final Phone phone) {
        Long phoneId = phone.getId();
        List<Color> colors = jdbcTemplate.query(SqlQueryConstants.PhoneDao.SELECT_COLORS_BELONGS_TO_PHONE_ID + phoneId,
                new BeanPropertyRowMapper(Color.class));
        phone.setColors(new HashSet<>(colors));
    }

    @Override
    public Integer countAvailablePhone(String searchText) {
        searchText = "%" + searchText.trim().toLowerCase() + "%";
        return jdbcTemplate.queryForObject(SqlQueryConstants.PhoneDao.COUNT_PHONES + SqlQueryConstants.PhoneDao.AVAILABLE_PHONES
                        + "AND lower(brand) LIKE ?", Integer.class,
                searchText);
    }

    @Override
    public boolean contains(Long key) {
        Long amount = jdbcTemplate.queryForObject(SqlQueryConstants.PhoneDao.COUNT_PHONES_WITH_ID + key, Long.class);
        return amount > 0;
    }

    @Override
    public List<Stock> getPhonesStocks(List<Phone> phones) {
        List<Stock> stocks = new ArrayList<>();
        for(Phone phone : phones){
            Stock stock = jdbcTemplate.queryForObject(SqlQueryConstants.PhoneDao.SELECT_STOCK + phone.getId(),
                    new StockPropertyRowMapper());
            stock.setPhone(phone);
            stocks.add(stock);
        }
        return stocks;
    }
}