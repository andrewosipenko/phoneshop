package com.es.core.exception;

public class PaginationException extends RuntimeException {
    public static final String PAGE_NUMBER_IS_NEGATIVE = "pageNumber <= 0";

    public PaginationException() {
        super(PAGE_NUMBER_IS_NEGATIVE);
    }
}
