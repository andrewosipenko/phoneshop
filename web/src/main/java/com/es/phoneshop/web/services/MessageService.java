package com.es.phoneshop.web.services;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {
    @Resource
    private MessageSource messageSource;

    public String getErrorMessage(BindingResult result) {
        String errors = new String();
        List<ObjectError> allErrors = result.getAllErrors();
        for (ObjectError error : allErrors) {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            errors = errors.concat(message);
        }
        return errors;
    }
}
