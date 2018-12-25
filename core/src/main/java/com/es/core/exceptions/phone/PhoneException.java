package com.es.core.exceptions.phone;

public class PhoneException extends RuntimeException {
    public PhoneException() {
        super();
    }

    public PhoneException(String message) {
        super(message);
    }

    public PhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneException(Throwable cause) {
        super(cause);
    }
}
