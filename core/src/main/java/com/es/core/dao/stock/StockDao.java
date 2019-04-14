package com.es.core.dao.stock;

import com.es.core.model.stock.Stock;

public interface StockDao {
    Stock findPhoneStock(Long id);

    void updateStock(Long id, Long quantity, Long reserved);
}
