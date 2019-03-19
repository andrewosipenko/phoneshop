package com.es.core.dao.stock;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockDaoImpl implements StockDao {
    private final static String QUERY_FOR_FIND_STOCK =
            "select stock from stocks where phoneId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long findPhoneQuantity(Long id) {
        return jdbcTemplate.queryForObject(QUERY_FOR_FIND_STOCK, Long.class, id);
    }
}
