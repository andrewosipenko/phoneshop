package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface StockDao {
    Optional<Stock> get(Long key);
    void save(Stock stock);
    List<Stock> findAll(int offset, int limit);
    void delete(Stock stock);
}
