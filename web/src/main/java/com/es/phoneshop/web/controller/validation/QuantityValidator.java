package com.es.phoneshop.web.controller.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class QuantityValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return QuantityInputWrapper.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuantityInputWrapper quantityInputWrapper = ((QuantityInputWrapper) o);
        long quantity = 0;
        try {
            quantity = Long.parseLong(quantityInputWrapper.getQuantity());
        } catch (NumberFormatException e) {
            errors.rejectValue("quantity", "Only digits are allowed");
        }
        if (quantity > 99) {
            errors.rejectValue("quantity", "You can't add more then 99 items");
        }
    }
}
