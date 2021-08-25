package com.es.phoneshop.web.controller.pages.exception;

import com.es.core.exceptions.DataFormValidateException;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStockException(OutOfStockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({ProductNotFoundException.class})
    protected ResponseEntity<String> handlePhoneNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataFormValidateException.class)
    protected ResponseEntity<String> handleDataValidationException(DataFormValidateException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
