package com.es.phoneshop.web.validate.sort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Collectors;

public class OrderByConstraintValidator implements ConstraintValidator<OrderByValid, String> {
    private Set<String> columnSet;

    private Set<String> orderSet;

    public OrderByConstraintValidator() {
        this.columnSet = new HashSet<>();
        this.orderSet = new HashSet<>();
    }

    @Override
    public void initialize(OrderByValid constraintAnnotation) {
        Set<String> columnSet = Arrays.stream(constraintAnnotation.columnEnum().getEnumConstants())
                .map(Enum::toString)
                .collect(Collectors.toSet());
        Set<String> orderSet = Arrays.stream(constraintAnnotation.orderEnum().getEnumConstants())
                .map(Enum::toString)
                .collect(Collectors.toSet());
        this.columnSet = columnSet;
        this.orderSet = orderSet;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] splits = value.split("_");

        if (splits.length != 2) {
            return false;
        }

        String column = splits[0];
        String order = splits[1];
        return columnSet.contains(column) && orderSet.contains(order);
    }
}
