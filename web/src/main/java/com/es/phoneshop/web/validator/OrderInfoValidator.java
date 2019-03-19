package com.es.phoneshop.web.validator;

import com.es.phoneshop.web.form.OrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource("classpath:/message/errorMessageEN.properties")
public class OrderInfoValidator implements Validator {
    private final static String PHONE_REGEX = "^[-+\\d]+";
    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";
    private static final String ADDRESS = "address";
    private static final String ADDITIONAL_INFO = "additionalInfo";
    private static final String PHONE = "phone";

    @Value("${message.empty}")
    private String messageEmptyValue;

    @Value("${massage.notPhoneFormat}")
    private String messageNotPhoneFormat;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderInfo orderInfo = (OrderInfo) o;
        validateFieldOnEmpty(NAME, orderInfo.getName(), errors);
        validateFieldOnEmpty(LAST_NAME, orderInfo.getLastName(), errors);
        validateFieldOnEmpty(ADDRESS, orderInfo.getAddress(), errors);
        validateFieldOnEmpty(ADDITIONAL_INFO, orderInfo.getAdditionalInfo(), errors);
        validateFieldOnEmpty(PHONE, orderInfo.getPhone(), errors);
        validateFieldOnPhone(PHONE, orderInfo.getPhone(), errors);
    }

    private void validateFieldOnEmpty(String field, String value, Errors errors) {
        if (value.isEmpty()) {
            errors.rejectValue(field, value, new String[]{field}, messageEmptyValue);
        }
    }

    private void validateFieldOnPhone(String field, String value, Errors errors) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            errors.rejectValue(field, value, new String[]{field}, messageNotPhoneFormat);
        }
    }
}
