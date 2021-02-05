package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.NotFoundPhoneCustomException;
import com.es.core.exceptions.OutOfStockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundPhoneCustomException.class)
    public ModelAndView handleExceptionNoSuchPhone(NotFoundPhoneCustomException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPages/notFoundPhone");
        modelAndView.addObject("errCode", exception.getErrCode());
        modelAndView.addObject("errMsg", exception.getErrMsg());
        return modelAndView;
    }

    @ExceptionHandler(OutOfStockException.class)
    public ModelAndView handleExceptionInStock(OutOfStockException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPages/notEnoughStockPhone");
        modelAndView.addObject("errCode", exception.getErrCode());
        modelAndView.addObject("errMsg", exception.getErrMsg());
        return modelAndView;
    }
}
