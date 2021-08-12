package com.es.core.dao.stockDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class StockDaoImpl implements StockDao  {

    private static final String GET_PHONE_FROM_STOCK_QUARY = "SELECT stock FROM stocks WHERE phoneId=?";
    private static final String UPDATE_STOCKS_SET_STOCK_WHERE_PHONE_ID = "UPDATE stocks SET stock=? WHERE phoneId=?";
    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public int getPhoneStock(Long phoneId) {
        return jdbcTemplate.queryForObject(GET_PHONE_FROM_STOCK_QUARY, new Object[] {phoneId}, Integer.class);
    }

    @Override
    public void reducePhoneStock(Long phoneId, Long quantity) {
        jdbcTemplate.update(UPDATE_STOCKS_SET_STOCK_WHERE_PHONE_ID, getPhoneStock(phoneId) - quantity, phoneId);
    }
}
