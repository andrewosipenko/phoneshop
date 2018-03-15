package com.es.core.dao.stock;

import com.es.core.model.phone.Stock;

import java.util.List;

public interface StockDao {
    List<Stock> getStocks(List<Long> phoneIdList);
}
