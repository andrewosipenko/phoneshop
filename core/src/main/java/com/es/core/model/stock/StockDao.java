package com.es.core.model.stock;

import com.es.core.model.phone.Stock;

import java.util.List;
import java.util.Map;

public interface StockDao {
    Map<Long, Stock> getStocks(List<Long> itemsIds);
    void updateStocks(List<Stock> stockList);
}
