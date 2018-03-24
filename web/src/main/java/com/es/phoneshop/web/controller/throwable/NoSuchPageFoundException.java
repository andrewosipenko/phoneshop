package com.es.phoneshop.web.controller.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchPageFoundException extends RuntimeException {
    public NoSuchPageFoundException() {}

    public NoSuchPageFoundException(String message) {
        super(message);
    }

    public NoSuchPageFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPageFoundException(Throwable cause) {
        super(cause);
    }
}
