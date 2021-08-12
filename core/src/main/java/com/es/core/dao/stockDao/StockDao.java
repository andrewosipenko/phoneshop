package com.es.core.dao.stockDao;

public interface StockDao {

    int getPhoneStock(Long phoneId);

    void reducePhoneStock(Long phoneId, Long quantity);
}
