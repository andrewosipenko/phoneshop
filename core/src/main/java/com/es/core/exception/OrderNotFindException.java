package com.es.core.exception;

public class OrderNotFindException extends RuntimeException {
    public static final String EXCEPTION_MESSAGE = "Order is not found";

    public OrderNotFindException() {
        super(EXCEPTION_MESSAGE);
    }

    public OrderNotFindException(String message) {
        super(message);
    }
}
