package com.es.core.dao;

public interface StockDao {
    Integer getStockFor(Long key);

    void updateReservationFor(Long key, Integer quantity);
}
