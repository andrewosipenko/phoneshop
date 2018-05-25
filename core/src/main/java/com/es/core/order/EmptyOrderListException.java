package com.es.core.order;

public class EmptyOrderListException extends Exception {
    public EmptyOrderListException() {
    }

    public EmptyOrderListException(String message) {
        super(message);
    }
}
