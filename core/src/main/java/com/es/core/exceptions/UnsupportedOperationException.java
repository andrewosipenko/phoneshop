package com.es.core.exceptions;

public class UnsupportedOperationException extends RuntimeException {

    public UnsupportedOperationException() {
        super("This operation is not supported yet");
    }
}
