package com.es.phoneshop.core.stock.dao;

import com.es.phoneshop.core.stock.model.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
        Stock stock = new Stock();
        stock.setReserved(resultSet.getInt("reserved"));
        stock.setStock(resultSet.getInt("stock"));
        return stock;
    }
}
