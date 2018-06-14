package com.es.core.model.stock;

import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Map;

public interface StockDao {

    Map<Phone, Stock> getStocksForPhones(List<Phone> phones);

    void reserveStocks(Order order);

    void applyReserved(Order order);

    void rejectReserved(Order order);
}
