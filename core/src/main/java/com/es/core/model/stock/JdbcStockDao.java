package com.es.core.model.stock;

import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.es.core.model.stock.StockDaoQueries.SELECT_STOCK_LIST_QUERY;
import static com.es.core.model.stock.StockDaoQueries.UPDATE_STOCK_QUERY;

@Component
public class JdbcStockDao implements StockDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    public void init() {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Map<Long, Stock> getStocks(List<Long> phoneIdList) {
        Map namedParameters = Collections.singletonMap("phoneIdList", phoneIdList);
        List<Stock> stocks =  namedParameterJdbcTemplate.query(SELECT_STOCK_LIST_QUERY, namedParameters, new BeanPropertyRowMapper<>(Stock.class));
        Map<Long, Stock> stockMap = new HashMap<>();
        stocks.forEach(e -> stockMap.put(e.getPhoneId(), e));
        return stockMap;
    }

    @Override
    public void updateStocks(List<Stock> newStockList) {
        jdbcTemplate.batchUpdate(UPDATE_STOCK_QUERY, getStockSetter(newStockList));
    }

    private BatchPreparedStatementSetter getStockSetter(List<Stock> stockList) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, stockList.get(i).getStock());
                preparedStatement.setInt(2, stockList.get(i).getReserved());
                preparedStatement.setLong(3, stockList.get(i).getPhoneId());
            }

            @Override
            public int getBatchSize() {
                return stockList.size();
            }
        };
    }
}
