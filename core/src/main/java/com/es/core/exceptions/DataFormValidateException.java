package com.es.core.exceptions;

import org.springframework.validation.BindingResult;

public class DataFormValidateException extends RuntimeException {

    private BindingResult bindingResult;

    public DataFormValidateException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public DataFormValidateException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    // todo: create exception controller
    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
