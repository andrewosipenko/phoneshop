package com.es.phoneshop.web.validator;

import com.es.phoneshop.web.form.OrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrderInfoValidator implements Validator {
    private final static String PHONE_REGEX = "^[-+\\d]+";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";
    private static final String ADDRESS = "address";
    private static final String ADDITIONAL_INFO = "additionalInfo";
    private static final String PHONE = "phone";

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderInfo orderInfo = (OrderInfo) o;
        if (orderInfo.getName().isEmpty()) {
            errors.rejectValue(NAME, orderInfo.getName(), new String[]{NAME},"Name should be not empty");
        }
        if (orderInfo.getLastName().isEmpty()) {
            errors.rejectValue(LAST_NAME, orderInfo.getLastName(), new String[]{LAST_NAME},"Last name should be not empty");
        }
        if (orderInfo.getAddress().isEmpty()) {
            errors.rejectValue(ADDRESS, orderInfo.getAddress(), new String[]{ADDRESS},"Address should be not empty");
        }
        if (orderInfo.getAdditionalInfo().isEmpty()) {
            errors.rejectValue(ADDITIONAL_INFO, orderInfo.getAdditionalInfo(), new String[]{ADDITIONAL_INFO},"Additional info should be not empty");
        }

        if (orderInfo.getPhone().isEmpty()) {
            errors.rejectValue(PHONE, orderInfo.getPhone(), new String[]{PHONE}, "Phone should be not empty");
        } else {
            Pattern pattern = Pattern.compile(PHONE_REGEX);
            Matcher matcher = pattern.matcher(orderInfo.getPhone());
            if (!matcher.matches()) {
                errors.rejectValue(PHONE, orderInfo.getPhone(), new String[]{PHONE}, "Not phone format");
            }
        }
    }
}
