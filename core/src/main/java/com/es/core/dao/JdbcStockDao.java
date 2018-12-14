package com.es.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStockDao implements StockDao {
    private static final String SQL_FOR_GETTING_STOCK_BY_PHONE_ID = "select stock from stocks where stocks.phoneId = ?";
    private static final String SQL_FOR_CHANGE_RESERVED_QUANTITY = "update stocks set reserved = reserved + ? where phoneId = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer getStockFor(Long key) {
        return jdbcTemplate.queryForObject(SQL_FOR_GETTING_STOCK_BY_PHONE_ID, Integer.class, key);
    }


    @Override
    public void updateReservationFor(Long key, Integer quantity) {
        jdbcTemplate.update(SQL_FOR_CHANGE_RESERVED_QUANTITY, quantity, key);
    }
}
