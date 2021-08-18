package com.es.phoneshop.web.controller.pages.exception;

import com.es.core.exceptions.OutOfStockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStockException(OutOfStockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
