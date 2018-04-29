package com.es.core.dao.phoneDao.rowMapper;

import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockPropertyRowMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
        Stock stock = new Stock();
        stock.setStock(resultSet.getLong("stock"));
        stock.setReserved(resultSet.getLong("reserved"));
        return stock;
    }
}
