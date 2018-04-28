package com.es.phoneshop.core.stock.dao;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;
import com.es.phoneshop.core.util.SQLQueries;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class JdbcStockDao implements StockDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Stock> get(Phone phone) {
        try {
            Stock stock = jdbcTemplate.queryForObject(SQLQueries.GET_STOCK, new StockRowMapper(), phone.getId());
            stock.setPhone(phone);
            return Optional.of(stock);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Stock stock) {
        jdbcTemplate.update(SQLQueries.UPDATE_STOCK, stock.getStock(), stock.getReserved(), stock.getPhone().getId());
    }
}
