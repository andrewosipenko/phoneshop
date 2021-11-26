package com.es.core.exception;

public class StockNotFindException extends RuntimeException {

    public static final String STOCK_S_RECORD_OF_PHONE_IS_NOT_FOUND = "Stock's record of phone is not found, id = ";

    public StockNotFindException(Long key) {
        super(STOCK_S_RECORD_OF_PHONE_IS_NOT_FOUND + key);
    }

    public StockNotFindException(String message) {
        super(message);
    }
}
