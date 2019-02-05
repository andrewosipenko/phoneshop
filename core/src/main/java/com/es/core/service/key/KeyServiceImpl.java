package com.es.core.service.key;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class KeyServiceImpl implements KeyService {
    private String SQL_COUNT_ORDER_KEY = "select count(*) from orderId2orderKey where orderKey = :orderKey";
    private SqlParameterSource sqlParameterSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public String generateOrderKey(){
        String orderKey;
        do {
            orderKey = UUID.randomUUID().toString().replaceAll("-", "");
        } while (isExistingKey(orderKey));
        return orderKey;
    }

    private boolean isExistingKey(String orderKey) {
        this.sqlParameterSource = new MapSqlParameterSource("orderKey", orderKey);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return this.namedParameterJdbcTemplate.queryForObject(SQL_COUNT_ORDER_KEY, sqlParameterSource, Long.class) != 0;
    }
}
