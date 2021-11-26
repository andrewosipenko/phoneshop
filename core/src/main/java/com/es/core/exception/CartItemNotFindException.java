package com.es.core.exception;

public class CartItemNotFindException extends RuntimeException {
    public static final String EXCEPTION_MESSAGE = "CartItem is not found";

    public CartItemNotFindException() {
        super(EXCEPTION_MESSAGE);
    }

    public CartItemNotFindException(String message) {
        super(message);
    }
}
