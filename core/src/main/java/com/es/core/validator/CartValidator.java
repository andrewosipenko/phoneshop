package com.es.core.validator;

import com.es.core.form.CartItemsInfo;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.stream.IntStream;

@Service
public class CartValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemsInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemsInfo cartItemsInfo = (CartItemsInfo) o;
        Long[] quantities = cartItemsInfo.getQuantity();

        IntStream.range(0, quantities.length)
                .filter(i -> quantities[i] < 1)
                .forEach(i -> errors.rejectValue("quantity", String.valueOf(i), new Long[]{quantities[i]}, "should be more then zero"));
    }
}
