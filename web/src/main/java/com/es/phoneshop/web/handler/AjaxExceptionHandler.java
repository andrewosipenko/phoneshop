package com.es.phoneshop.web.handler;

import com.es.core.exception.PhoneNotFoundException;
import com.es.phoneshop.web.bean.cart.CartInfo;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.es.phoneshop.web.controller.constants.AjaxConstants.WRONG_INPUT;

@ControllerAdvice
public class AjaxExceptionHandler {

    @ExceptionHandler(PhoneNotFoundException.class)
    protected ResponseEntity<CartInfo> handleResourceNotFound(PhoneNotFoundException ex){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CartInfo("Requested phone does not found"));
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<CartInfo> handleInvalidFormat(InvalidFormatException ex){

        return ResponseEntity
                .badRequest()
                .body(new CartInfo(WRONG_INPUT));
    }
}
