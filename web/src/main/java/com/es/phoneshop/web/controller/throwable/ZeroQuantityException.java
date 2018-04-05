package com.es.phoneshop.web.controller.throwable;

public class ZeroQuantityException extends RuntimeException {
    public ZeroQuantityException() {
    }

    public ZeroQuantityException(String message) {
        super(message);
    }

    public ZeroQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZeroQuantityException(Throwable cause) {
        super(cause);
    }
}
