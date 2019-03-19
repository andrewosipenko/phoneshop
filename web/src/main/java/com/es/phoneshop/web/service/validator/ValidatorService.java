package com.es.phoneshop.web.service.validator;

import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

public interface ValidatorService {
    boolean isAddValidator(WebDataBinder webDataBinder, Validator validator, String baseValidator);
}
