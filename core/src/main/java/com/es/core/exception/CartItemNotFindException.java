package com.es.core.exception;

public class CartItemNotFindException extends RuntimeException {
    public CartItemNotFindException(String message) {
        super(message);
    }
}
