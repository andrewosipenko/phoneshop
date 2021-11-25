package com.es.core.exceptions;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super("!!! Not enough stock !!!");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
