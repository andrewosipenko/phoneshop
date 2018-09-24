package com.es.phoneshop.web.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Page with such ID does not exist")
public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(){
        super("Page with such ID does not exist");
    }

    public PageNotFoundException(String message){
        super(message);
    }
}
