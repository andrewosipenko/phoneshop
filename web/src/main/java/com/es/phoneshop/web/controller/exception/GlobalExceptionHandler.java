package com.es.phoneshop.web.controller.exception;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    @ExceptionHandler(InvalidUrlParamException.class)
    public ModelAndView handleInvalidUrlParamException(InvalidUrlParamException exception) {
        ModelAndView mv = new ModelAndView("error/errorPage");
        mv.addObject(ERROR_MESSAGE_ATTRIBUTE, exception.getLocalizedMessage());
        return mv;
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ModelAndView handleBadSqlGrammarException(BadSqlGrammarException exception) {
        ModelAndView mv = new ModelAndView("error/errorPage");
        mv.addObject(ERROR_MESSAGE_ATTRIBUTE, "Bad sql grammar exception\n" +
                "Maybe you've done incorrect url request");
        return mv;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(NumberFormatException e){
        ModelAndView mv = new ModelAndView("error/errorPage");
        mv.addObject(ERROR_MESSAGE_ATTRIBUTE, e.getLocalizedMessage());
        return mv;
    }
}