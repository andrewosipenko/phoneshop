package com.es.phoneshop.web.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Order with such ID does not exist")
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){
        super("Page with such ID does not exist");
    }

    public OrderNotFoundException(String message){
        super(message);
    }
}