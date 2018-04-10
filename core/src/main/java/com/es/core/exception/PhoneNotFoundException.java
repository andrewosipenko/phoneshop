package com.es.core.exception;

public class PhoneNotFoundException extends Exception {
    private Long phoneId;

    public PhoneNotFoundException() {
    }

    public PhoneNotFoundException(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getPhoneId() {
        return phoneId;
    }
}
