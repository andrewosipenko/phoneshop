package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class JdbcStockDao implements StockDao {

    private final String SQL_SELECT_STOCK_BY_ID = "select * from stocks where phoneID = :phoneId";

    @Resource
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SqlParameterSource sqlParameterSource;
    private BeanPropertyRowMapper<Stock> stockBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Stock.class);

    @Override
    public Optional<Stock> get(Long key) {
        this.sqlParameterSource = new MapSqlParameterSource("phoneID", key);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Stock stock = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_STOCK_BY_ID, sqlParameterSource, stockBeanPropertyRowMapper);
        return Optional.ofNullable(stock);
    }

    @Override
    public void save(Stock stock) {

    }

    @Override
    public List<Stock> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public void delete(Stock stock) {

    }
}
