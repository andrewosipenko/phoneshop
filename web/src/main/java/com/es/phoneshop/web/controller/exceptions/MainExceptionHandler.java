package com.es.phoneshop.web.controller.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MainExceptionHandler {
    private final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private final String PAGE_ERROR = "/error/error";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleInvalidParametersInUrlException(Exception exception){
        ModelAndView modelAndView = new ModelAndView(PAGE_ERROR);
        modelAndView.addObject(ERROR_MESSAGE_ATTRIBUTE, exception.getLocalizedMessage());
        return modelAndView;
    }
}
