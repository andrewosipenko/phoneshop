package com.es.core.dao.stock;

import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JdbcStockDao implements StockDao {

    private final static String SELECT_STOCK_LIST_QUERY = "select * from stocks where phoneId IN (:phoneIdList)";

    @Resource
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    public void init() {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<Stock> getStocks(List<Long> phoneIdList) {
        Map namedParameters = Collections.singletonMap("phoneIdList", phoneIdList);
        return namedParameterJdbcTemplate.query(SELECT_STOCK_LIST_QUERY, namedParameters, new BeanPropertyRowMapper<Stock>(Stock.class));
    }
}
