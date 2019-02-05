package com.es.core.dao.stock;

import com.es.core.model.stock.Stock;

import java.util.List;
import java.util.Optional;

public interface StockDao {
    Optional<Stock> get(Long key);
    void update(List<Stock> stockList);
}
