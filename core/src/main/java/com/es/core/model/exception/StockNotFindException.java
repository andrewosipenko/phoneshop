package com.es.core.model.exception;

public class StockNotFindException extends RuntimeException{
    public StockNotFindException(String message) {
        super(message);
    }
}
