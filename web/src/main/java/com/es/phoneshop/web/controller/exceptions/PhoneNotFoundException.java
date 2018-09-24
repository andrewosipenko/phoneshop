package com.es.phoneshop.web.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Phone with such ID does not exist")
public class PhoneNotFoundException extends RuntimeException{
    public PhoneNotFoundException(){
        super("Phone with such ID does not exist");
    }

    public PhoneNotFoundException(String message){
        super(message);
    }
}
