package com.es.core.model.DAO.stock;

import com.es.core.model.entity.phone.Stock;

import java.util.List;
import java.util.Optional;

public interface StockDao {

    Optional<Stock> get(Long phoneId);

    List<Stock> getCorrespondingValue(List<Long> phoneIds);
}
