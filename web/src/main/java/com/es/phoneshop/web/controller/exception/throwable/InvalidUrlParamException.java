package com.es.phoneshop.web.controller.exception.throwable;

public class InvalidUrlParamException extends Exception {
    public InvalidUrlParamException(String message){
        super(message);
    }
}