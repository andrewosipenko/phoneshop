package com.es.phoneshop.web.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.es.phoneshop.web.form.CartItemInfo;

@Service
@PropertySource("classpath:/message/errorMessageEN.properties")
public class CartItemInfoValidator implements Validator {
    private static final String FIELD_QUANTITY = "quantity";

    @Value("${message.notPositive}")
    private String messageNotPositive;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemInfo cartItemInfo = (CartItemInfo) o;
        if (cartItemInfo.getQuantity() <= 0) {
            errors.rejectValue(FIELD_QUANTITY, String.valueOf(cartItemInfo.getQuantity()), messageNotPositive);
        }
    }
}
