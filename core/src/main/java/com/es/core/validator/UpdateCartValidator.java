package com.es.core.validator;

import com.es.core.form.cart.CartForm;
import com.es.core.form.cart.CartFormItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UpdateCartValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartForm updateCartForm = (CartForm) o;
        List<CartFormItem> cartFormList = updateCartForm.getCartFormList();
        for (int i = 0; i < cartFormList.size(); i++) {
            Long phoneId = cartFormList.get(i).getPhoneId();
            Long quantity = cartFormList.get(i).getQuantity();
            if (phoneId == null) {
                errors.rejectValue("cartFormList[" + i + "].phoneId", "phoneId.invalid");
            }
            if (quantity == null || quantity < 1L) {
                errors.rejectValue("cartFormList[" + i + "].quantity", "quantity.invalid");
            }
        }
    }
}
