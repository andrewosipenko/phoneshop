package com.es.core.validator;

import com.es.core.form.cart.AddCartForm;
import com.es.core.form.cart.UpdateCartForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UpdateCartValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UpdateCartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UpdateCartForm updateCartForm = (UpdateCartForm) o;
        List<AddCartForm> cartFormList = updateCartForm.getCartFormList();
        for (int i = 0; i < cartFormList.size(); i++) {
            Long phoneId = cartFormList.get(i).getPhoneId();
            Long quantity = cartFormList.get(i).getQuantity();
            if (phoneId == null) {
                errors.rejectValue("cartForm[" + i + "].phoneId", "phoneId.invalid");
            }
            if (quantity == null || quantity < 1L) {
                errors.rejectValue("cartForm[" + i + "].quantity", "quantity.invalid");
            }
        }
    }
}
