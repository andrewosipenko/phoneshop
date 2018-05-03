package com.es.core.dao.stockDao;

import com.es.core.model.stock.Stock;

import java.util.List;

public interface StockDao {
    Stock getStockByPhoneId(Long phoneId);
    void updateStocks(List<Stock> stocks);
}
