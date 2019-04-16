package com.es.phoneshop.web.controller.exceptions;

public class InvalidParametersInUrlException extends RuntimeException {
    public InvalidParametersInUrlException() {
        super();
    }

    public InvalidParametersInUrlException(String message) {
        super(message);
    }

    public InvalidParametersInUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParametersInUrlException(Throwable cause) {
        super(cause);
    }
}
