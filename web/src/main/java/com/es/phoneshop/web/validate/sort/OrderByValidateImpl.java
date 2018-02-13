package com.es.phoneshop.web.validate.sort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedList;
import java.util.List;

public class OrderByValidateImpl implements ConstraintValidator<ValidateOrderBy, String> {

    private List<String> columnList;

    private List<String> orderList;

    @Override
    public void initialize(ValidateOrderBy constraintAnnotation) {
        columnList = new LinkedList<>();
        orderList = new LinkedList<>();

        Class<? extends Enum<?>> enumClass = constraintAnnotation.columnEnum();
        for(Enum enumVal : enumClass.getEnumConstants()) {
            columnList.add(enumVal.toString());
        }

        enumClass = constraintAnnotation.orderEnum();
        for(Enum enumVal : enumClass.getEnumConstants()) {
            orderList.add(enumVal.toString());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] splits = value.split("_");

        if (splits.length != 2) {
            return false;
        }

        String column = splits[0];
        String order = splits[1];
        return columnList.contains(column) && orderList.contains(order);
    }
}
