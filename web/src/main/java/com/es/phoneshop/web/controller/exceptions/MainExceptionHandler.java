package com.es.phoneshop.web.controller.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MainExceptionHandler {
    private final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    @ExceptionHandler(InvalidParametersInUrlException.class)
    public ModelAndView handleInvalidParametersInUrlException(InvalidParametersInUrlException exception){
        ModelAndView modelAndView = new ModelAndView("/error/error");
        modelAndView.addObject(ERROR_MESSAGE_ATTRIBUTE, exception.getLocalizedMessage());
        return modelAndView;
    }
}
