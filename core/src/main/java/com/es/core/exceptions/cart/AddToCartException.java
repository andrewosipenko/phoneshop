package com.es.core.exceptions.cart;

public class AddToCartException extends RuntimeException {
    public AddToCartException() {
        super();
    }

    public AddToCartException(String message) {
        super(message);
    }

    public AddToCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddToCartException(Throwable cause) {
        super(cause);
    }
}
