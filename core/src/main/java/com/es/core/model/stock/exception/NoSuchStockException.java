package com.es.core.model.stock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@ResponseBody
public class NoSuchStockException extends RuntimeException {
    public NoSuchStockException(){
        super();
    }
}
