package com.es.core.model.phone;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper implements RowMapper<Stock> {

    public static final String STOCK = "stock";
    public static final String RESERVED = "reserved";

    @Override
    public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
        Stock stock = new Stock();
        stock.setStock(resultSet.getInt(STOCK));
        stock.setReserved(resultSet.getInt(RESERVED));
        return stock;
    }
}
