package com.es.core.exception;

public class PhoneNotFindException extends RuntimeException {

    public static final String PHONE_IS_NOT_FOUND = "Phone is not found, id = ";

    public PhoneNotFindException(Long phoneId) {
        super(PHONE_IS_NOT_FOUND + phoneId);
    }

    public PhoneNotFindException(String message) {
        super(message);
    }
}
