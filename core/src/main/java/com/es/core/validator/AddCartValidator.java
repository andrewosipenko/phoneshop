package com.es.core.validator;

import com.es.core.form.cart.CartFormItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddCartValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartFormItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartFormItem cartForm = (CartFormItem) o;
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
