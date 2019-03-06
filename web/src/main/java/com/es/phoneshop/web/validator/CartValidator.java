package com.es.phoneshop.web.validator;

import com.es.phoneshop.web.form.CartItemsInfo;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
        String[] quantitiesStirng = cartItemsInfo.getQuantity();
        List<Long> quantities = new ArrayList<>();

        for (int i = 0; i < quantitiesStirng.length; i++) {
            try {

            } catch (NumberFormatException e) {
                errors.rejectValue("quantity", String.valueOf(i), new Long[]{quantities.get(i)}, "should be number");
            }
        }
        IntStream.range(0, quantities.size())
                .filter(i -> quantities.get(i) < 1)
                .forEach(i -> errors.rejectValue("quantity", String.valueOf(i), new Long[]{quantities.get(i)}, "should be more then zero"));
    }
}
