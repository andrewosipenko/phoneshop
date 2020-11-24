package com.es.phoneshop.web.controller.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class QuantityValidator implements Validator {

    private static final String NOT_NUM_ERROR = "Only digits are allowed";
    private static final String THRESHOLD_ERROR = "You can't add more then 99 items";
    private static final String NOT_POSITIVE_VALUE_ERROR = "Only positive values are allowed";

    private static final int QUANTITY_THRESHOLD = 99;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuantityInputWrapper.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuantityInputWrapper quantityInputWrapper = ((QuantityInputWrapper) o);
        long quantity;
        try {
            quantity = Long.parseLong(quantityInputWrapper.getQuantity());
        } catch (NumberFormatException e) {
            errors.reject("quantity", NOT_NUM_ERROR);
            return;
        }
        if (quantity > QUANTITY_THRESHOLD) {
            errors.reject("quantity", THRESHOLD_ERROR);
        }
        if (quantity <= 0) {
            errors.reject("quantity", NOT_POSITIVE_VALUE_ERROR);
        }
    }
}
