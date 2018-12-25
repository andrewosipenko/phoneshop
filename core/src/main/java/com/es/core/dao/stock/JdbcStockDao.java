package com.es.core.dao.stock;

import com.es.core.exceptions.stock.OutOfStockException;
import com.es.core.model.stock.Stock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcStockDao implements StockDao {

    private final String SQL_SELECT_STOCK_BY_ID = "select * from stocks where phoneID = :phoneId";
    private final String SQL_UPDATE_STOCK_BY_ID = "update stocks set stock = :stock, reserved = :reserved where phoneId = :phone.id";

    @Resource
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SqlParameterSource sqlParameterSource;
    private BeanPropertyRowMapper<Stock> stockBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Stock.class);

    @Override
    public Optional<Stock> get(Long key) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("phoneID", key);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            Stock stock = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_STOCK_BY_ID, sqlParameterSource, stockBeanPropertyRowMapper);
            return Optional.ofNullable(stock);
        } catch (EmptyResultDataAccessException ex){
            throw new OutOfStockException();
        }
    }

    @Override
    public void update(List<Stock> stockList) {
        for (Stock stock : stockList) {
            this.namedParameterJdbcTemplate.update(SQL_UPDATE_STOCK_BY_ID, getAllValuesStock(stock));
        }
    }

    private Map<String, Object> getAllValuesStock(Stock stock) {
        Map<String, Object> allValuesStock = new HashMap<>();
        allValuesStock.put("stock", stock.getStock());
        allValuesStock.put("reserved", stock.getReserved());
        allValuesStock.put("phoneId", stock.getPhone().getId());
        return allValuesStock;
    }
}
