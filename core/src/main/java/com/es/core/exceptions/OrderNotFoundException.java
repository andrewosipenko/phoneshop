package com.es.core.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Your order not found");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
