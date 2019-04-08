package com.es.core.dao.stock;

import com.es.core.model.stock.Stock;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockDaoImpl implements StockDao {
    private static final String QUERY_FOR_FIND_STOCK =
            "select * from stocks where phoneId = ?";
    private static final String QUERY_TO_UPDATE_STOCK =
            "update stocks SET stock = ?, reserved = ? where phoneId = ?";
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Stock findPhoneStock(Long id) {
        return jdbcTemplate.queryForObject(QUERY_FOR_FIND_STOCK, new BeanPropertyRowMapper<>(Stock.class), id);
    }

    @Override
    public void updateStock(Long id, Long quantity, Long reserved) {
        jdbcTemplate.update(QUERY_TO_UPDATE_STOCK, quantity, reserved, id);
    }
}
