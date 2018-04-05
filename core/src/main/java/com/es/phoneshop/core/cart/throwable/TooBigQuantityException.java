package com.es.phoneshop.core.cart.throwable;

public class TooBigQuantityException extends RuntimeException {
    public TooBigQuantityException() {
    }

    public TooBigQuantityException(String message) {
        super(message);
    }

    public TooBigQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooBigQuantityException(Throwable cause) {
        super(cause);
    }
}
