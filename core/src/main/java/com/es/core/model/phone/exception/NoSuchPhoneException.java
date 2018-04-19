package com.es.core.model.phone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchPhoneException extends RuntimeException {
    public NoSuchPhoneException(){
        super();
    }
}