package com.es.phoneshop.web.handler;

import com.es.core.exception.PhoneNotFoundException;
import com.es.phoneshop.web.exception.InvalidCartItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AjaxExceptionHandler {

    @ExceptionHandler(PhoneNotFoundException.class)
    protected ResponseEntity<String> handlePhoneNotFound(PhoneNotFoundException ex){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Requested phone does not found");
    }

    @ExceptionHandler(InvalidCartItemException.class)
    private @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST) String handleInvalidFormat() {
        return "Positive value expected";
    }
}
