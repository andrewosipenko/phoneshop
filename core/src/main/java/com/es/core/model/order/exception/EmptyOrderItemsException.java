package com.es.core.model.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyOrderItemsException extends RuntimeException{
    public EmptyOrderItemsException(){
        super();
    }
}
