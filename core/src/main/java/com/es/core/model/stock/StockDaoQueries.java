package com.es.core.model.stock;

public interface StockDaoQueries {
    String SELECT_STOCK_LIST_QUERY = "select * from stocks where phoneId IN (:phoneIdList)";
    String UPDATE_STOCK_QUERY = "update stocks set stock = ?, reserved = ? where phoneId = ?";
}
