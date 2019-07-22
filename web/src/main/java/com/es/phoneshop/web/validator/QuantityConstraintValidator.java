package com.es.phoneshop.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityConstraintValidator implements ConstraintValidator<IsValidQuantity, String> {


    @Override
    public void initialize(IsValidQuantity constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            int val = Integer.parseInt(s);
            return val > 0;
        } catch (Throwable throwable) {
            return false;
        }
    }
}
