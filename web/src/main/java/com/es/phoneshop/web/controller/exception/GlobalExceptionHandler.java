package com.es.phoneshop.web.controller.exception;

import com.es.phoneshop.web.controller.exception.throwable.InvalidUrlParamException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    @ExceptionHandler(InvalidUrlParamException.class)
    public ModelAndView handleInvalidUrlParamException(InvalidUrlParamException exception) {
        ModelAndView mv = new ModelAndView("error/errorPage");
        mv.addObject(ERROR_MESSAGE_ATTRIBUTE, exception.getLocalizedMessage());
        return mv;
    }
}