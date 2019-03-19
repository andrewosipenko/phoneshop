package com.es.phoneshop.web.validator;

import com.es.phoneshop.web.form.CartItemsInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.stream.IntStream;

@Service
@PropertySource("classpath:/message/errorMessageEN.properties")
public class CartValidator implements Validator {
    @Value("${message.notPositive}")
    private String messageNotPositive;

    @Value("${message.notNumberFormat}")
    private String messageNotNumberFormat;

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
                    errors.rejectValue("quantity", String.valueOf(i), new String[]{quantities[i]}, messageNotPositive);
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("quantity", String.valueOf(i), new String[]{quantities[i]}, messageNotNumberFormat);
            }
        });
    }
}
