package com.es.phoneshop.web.validator;

import com.es.phoneshop.web.form.CartItemsInfo;
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
        String[] quantities = cartItemsInfo.getQuantity();

        IntStream.range(0, quantities.length).forEach(i -> {
            try {
                Long quantity = Long.valueOf(quantities[i]);
                if (quantity < 0) {
                    errors.rejectValue("quantity", String.valueOf(i), new String[]{quantities[i]}, "should be more then zero");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("quantity", String.valueOf(i), new String[]{quantities[i]}, "should be number");
            }
        });
    }
}
