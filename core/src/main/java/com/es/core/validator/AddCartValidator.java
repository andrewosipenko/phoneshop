package com.es.core.validator;

import com.es.core.form.CartForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddCartValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartForm cartForm = (CartForm) o;
        Long phoneId = cartForm.getPhoneId();
        Long quantity = cartForm.getQuantity();
        if (phoneId == null){
            errors.reject("phoneId", "phoneId.invalid");
        }
        if (quantity == null || quantity < 1L){
            errors.reject("quantity", "quantity.invalid");
        }
    }
}
