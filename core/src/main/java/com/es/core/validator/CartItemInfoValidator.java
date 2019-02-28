package com.es.core.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.es.phoneshop.web.form.CartItemInfo;

@Service
public class CartItemInfoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemInfo cartItemInfo = (CartItemInfo) o;
        if (cartItemInfo.getQuantity() <= 0) {
            errors.rejectValue("quantity", "should be positive", "should be more then zero");
        }
    }
}
