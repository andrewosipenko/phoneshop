package com.es.phoneshop.web.service.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

@Service
public class ValidatorServiceImpl implements ValidatorService{
    @Override
    public boolean isAddValidator(WebDataBinder webDataBinder, Validator validator, String baseValidator) {
        return webDataBinder.getTarget() != null
                && validator.supports(webDataBinder.getTarget().getClass())
                && !validator.getClass().getName().contains(baseValidator);
    }
}
