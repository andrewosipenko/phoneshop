package com.es.phoneshop.web.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUrlParamException extends Exception {
    public InvalidUrlParamException(String message){
        super(message);
    }
}