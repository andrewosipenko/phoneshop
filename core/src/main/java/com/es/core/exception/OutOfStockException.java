package com.es.core.exception;

public class OutOfStockException extends RuntimeException {

    public static final String ERROR_MESSAGE = "Out of stock phone with id = ";

    public OutOfStockException(String id) {
        super(ERROR_MESSAGE + id);
    }
}
