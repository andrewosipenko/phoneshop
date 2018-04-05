package com.es.phoneshop.web.controller.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectFormFormatException extends RuntimeException {
    public IncorrectFormFormatException() {
    }

    public IncorrectFormFormatException(String message) {
        super(message);
    }

    public IncorrectFormFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectFormFormatException(Throwable cause) {
        super(cause);
    }
}
