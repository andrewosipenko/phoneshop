package com.es.core.validation.validator;

import com.es.core.form.updateForm.CartFormItem;
import com.es.core.form.updateForm.UpdateCartForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
@Component
public class UpdateCartFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UpdateCartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UpdateCartForm updateCartForm = (UpdateCartForm) o;
        List<CartFormItem> cartFormItemList = updateCartForm.getCartFormItems();
        for(int i = 0; i < cartFormItemList.size(); ++i) {
            Long quantity = cartFormItemList.get(i).getQuantity();
            Long phoneId = cartFormItemList.get(i).getPhoneId();
            if(phoneId == null){
                errors.rejectValue("cartFormItems[" + i + "].phoneId", "phoneId.required");
            }
            if(quantity == null || quantity < 1L) {
                errors.rejectValue("cartFormItems[" + i + "].quantity", "quantity.invalid");
            }
        }
    }
}
