package com.es.core.service.stock;

import com.es.core.model.stock.Stock;

public interface StockService {
    Stock findPhoneStock(Long id);

    void addReserved(Long id, Long reserved);

    void replaceReservedToStock(Long id, Long reserved);

    void deleteReserved(Long id, Long reserved);
}
