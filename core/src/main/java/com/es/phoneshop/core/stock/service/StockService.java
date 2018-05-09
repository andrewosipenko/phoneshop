package com.es.phoneshop.core.stock.service;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.stock.model.Stock;

import java.util.Optional;

public interface StockService {
    Optional<Stock> getStock(Phone phone);

    void update(Stock stock);
}
