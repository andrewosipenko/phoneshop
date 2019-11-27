package com.es.core.model.stock.exception;

public class OutOfStockException extends Exception {
    public OutOfStockException(String message){
        super(message);
    }
}
