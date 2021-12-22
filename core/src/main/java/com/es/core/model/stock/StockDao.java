package com.es.core.model.stock;

import com.es.core.exception.PhoneNotFindException;

public interface StockDao {
    int getStock(Long id) throws PhoneNotFindException;

    int getReserved(Long id) throws PhoneNotFindException;

    boolean setReserved(Long id, Long quantity) throws PhoneNotFindException;
}
