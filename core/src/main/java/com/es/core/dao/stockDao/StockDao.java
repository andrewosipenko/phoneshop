package com.es.core.dao.stockDao;

import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;

import java.util.List;

public interface StockDao {
    List<Stock> getPhonesStocks(List<Phone> phones);
    void updateStocks(List<Stock> stocks);
}
