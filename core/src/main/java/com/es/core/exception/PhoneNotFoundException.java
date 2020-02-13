package com.es.core.exception;

public class PhoneNotFoundException extends Exception {
    private Long id;

    public PhoneNotFoundException(){};

    public PhoneNotFoundException(Long id) {
        this.id = id;
    }

    public PhoneNotFoundException(String message, Long i) {
        super(message);
        id = i;
    }

    public Long getId() {
        return id;
    }

}
