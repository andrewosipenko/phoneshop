package com.es.core.model.stock;

import com.es.core.exception.OutOfStockException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.order.Order;

import java.util.List;

public interface StockService {
    int getAvailablePhoneStock(Long id) throws PhoneNotFindException;

    List<Long> updateStocks(Order order) throws OutOfStockException;
}
