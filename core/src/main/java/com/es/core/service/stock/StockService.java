package com.es.core.service.stock;

import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getPhonesStocks(List<Phone> phones);
    void updateStocks(Order order, boolean flag);
    void reduceReserved(Order order);
}
