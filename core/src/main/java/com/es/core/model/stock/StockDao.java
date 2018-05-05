package com.es.core.model.stock;

import java.util.List;
import java.util.Map;

public interface StockDao {
    Map<Long, Stock> getStocks(List<Long> itemsIds);
    void updateStocks(List<Stock> stockList);
}
