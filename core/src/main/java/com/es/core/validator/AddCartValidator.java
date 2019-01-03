package com.es.core.validator;

import com.es.core.form.cart.AddCartForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddCartValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AddCartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddCartForm cartForm = (AddCartForm) o;
        Long phoneId = cartForm.getPhoneId();
        Long quantity = cartForm.getQuantity();
        if (phoneId == null){
            errors.rejectValue("phoneId", "phoneId.invalid");
        }
        if (quantity == null || quantity < 1L){
            errors.rejectValue("quantity", "quantity.invalid");
        }
    }
}
