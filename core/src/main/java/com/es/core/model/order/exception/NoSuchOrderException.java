package com.es.core.model.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchOrderException extends RuntimeException{
    public NoSuchOrderException(){
        super();
    }
}
