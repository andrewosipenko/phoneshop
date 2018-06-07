package com.es.core.model.stock;

import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JdbcStockDao implements StockDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String SQL_STOCKS_QUERY = "SELECT * FROM stocks WHERE stocks.phoneId IN (%s)";

    @Override
    public Map<Long, Stock> getStocksForPhones(List<Phone> phones) {
        String inParameter = String.join(
                ", ",
                phones.stream().map((p)->p.getId().toString()).collect(Collectors.toList())
        );

        List<Stock> stocks = jdbcTemplate.query(
                String.format(SQL_STOCKS_QUERY, inParameter),
                new StocksResultSetExtractor(phones)
        );

       return stocks.stream().collect(Collectors.toMap((s)->s.getPhone().getId(), Function.identity()));
    }
}
