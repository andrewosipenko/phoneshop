package com.es.core.model.stock;

import com.es.core.exception.PhoneNotFindException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JdbcStockDao implements StockDao {
    public static final String SELECT_STOCK_FROM_STOCKS_WHERE_PHONE_ID = "select stock from stocks where phoneId=?";
    public static final String SELECT_RESERVED_FROM_STOCKS_WHERE_PHONE_ID = "select reserved from stocks where phoneId=?";
    public static final String SELECT_RESERVED_FROM_STOCKS_WHERE_PHONE_ID1 = "select reserved from stocks where phoneId=?";
    public static final String SELECT_STOCK_FROM_STOCKS_WHERE_PHONE_ID1 = "select stock from stocks where phoneId=?";
    public static final String UPDATE_STOCKS_SET_RESERVED_WHERE_ID = "update stocks set reserved=? where phoneId=?";
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getStock(Long id) throws PhoneNotFindException {
        try {
            return jdbcTemplate.queryForObject(SELECT_STOCK_FROM_STOCKS_WHERE_PHONE_ID, new Object[]{id}, Integer.class);
        } catch (DataAccessException e) {
            throw new PhoneNotFindException(id);
        }
    }

    @Override
    public int getReserved(Long id) throws PhoneNotFindException {
        try {
            return jdbcTemplate.queryForObject(SELECT_RESERVED_FROM_STOCKS_WHERE_PHONE_ID, new Object[]{id}, Integer.class);
        } catch (DataAccessException e) {
            throw new PhoneNotFindException(id);
        }
    }

    @Override
    public boolean setReserved(Long id, Long quantity) throws PhoneNotFindException {
        Long reserved = jdbcTemplate.queryForObject(SELECT_RESERVED_FROM_STOCKS_WHERE_PHONE_ID1, new Object[]{id}, Long.class);
        Long stock = jdbcTemplate.queryForObject(SELECT_STOCK_FROM_STOCKS_WHERE_PHONE_ID1, new Object[]{id}, Long.class);
        if (stock < reserved + quantity) {
            return false;
        } else {
            jdbcTemplate.update(UPDATE_STOCKS_SET_RESERVED_WHERE_ID, reserved + quantity, id);
        }
        return true;
    }
}
