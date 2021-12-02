package com.es.core.model.stock;

import com.es.core.exception.PhoneNotFindException;

public interface StockService {
    int getAvailablePhoneStock(Long id) throws PhoneNotFindException;
}
