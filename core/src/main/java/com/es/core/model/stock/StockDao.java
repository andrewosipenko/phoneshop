package com.es.core.model.stock;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Map;

public interface StockDao {

//    Stock getStockForId(Long id);

    Map<Long, Stock> getStocksForPhones(List<Phone> phones);
}
