package com.es.core.validation.validator;

import com.es.core.form.cart.AddToCartForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddToCartFormValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return AddToCartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddToCartForm addToCartForm = (AddToCartForm) o;
        Long quantity = addToCartForm.getQuantity();
        Long phoneId = addToCartForm.getPhoneId();
        if(phoneId == null){
            errors.rejectValue("phoneId", "phoneId.required");
        }
        if(quantity == null || quantity < 1L){
            errors.rejectValue("quantity", "quantity.invalid");
        }
    }
}