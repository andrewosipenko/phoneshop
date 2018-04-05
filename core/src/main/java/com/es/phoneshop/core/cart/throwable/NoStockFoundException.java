package com.es.phoneshop.core.cart.throwable;

public class NoStockFoundException extends RuntimeException {
    public NoStockFoundException() {
    }

    public NoStockFoundException(String message) {
        super(message);
    }

    public NoStockFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoStockFoundException(Throwable cause) {
        super(cause);
    }
}
