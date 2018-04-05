package com.es.phoneshop.core.cart.throwable;

public class NoSuchPhoneException extends RuntimeException {
    public NoSuchPhoneException() {
    }

    public NoSuchPhoneException(String message) {
        super(message);
    }

    public NoSuchPhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPhoneException(Throwable cause) {
        super(cause);
    }
}
