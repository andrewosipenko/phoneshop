package com.es.core.model.order.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderInformationConstraintValidator implements ConstraintValidator<IsValidPhone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() > 0 && s.charAt(0) == '+';
    }
}
