package com.es.phoneshop.web.controller.validation;

import com.es.phoneshop.web.controller.dto.AddPhoneRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class AddPhoneDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AddPhoneRequestDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddPhoneRequestDTO addPhoneRequestDTO = ((AddPhoneRequestDTO) o);
        long quantity = 0;
        try {
            quantity = Long.parseLong(addPhoneRequestDTO.getQuantity());
        } catch (NumberFormatException e) {
            errors.rejectValue("quantity", "Only digits are allowed");
        }
        if (quantity > 99) {
            errors.rejectValue("quantity", "You can't add more then 99 items");
        }
    }
}
