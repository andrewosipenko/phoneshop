package com.es.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class JdbcStockDao implements StockDao {
    private static final String SQL_FOR_GETTING_STOCK_BY_PHONE_ID = "select stock from stocks where stocks.phoneId = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public Integer getStockFor(Long key) {
        return jdbcTemplate.queryForObject(SQL_FOR_GETTING_STOCK_BY_PHONE_ID, Integer.class, key);
    }
}
