package com.es.phoneshop.core.stock.dao;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;

import java.util.Optional;

public interface StockDao {
    Optional<Stock> get(Phone phone);

    void save(Stock stock);
}
