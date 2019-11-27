package com.es.phoneshop.web.controller.pages.ajax;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageCodeResolveService {
    @Resource
    private MessageSource messageSource;

    public String getErrorMessage(BindingResult result){
        StringBuffer errors = new StringBuffer();
        List<ObjectError> allErrors = result.getAllErrors();
        for (ObjectError error : allErrors) {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            errors.append(message + "<br>");
        }
        return errors.toString();
    }
}