package com.es.phoneshop.web.controller.pages;

import com.es.core.service.phone.PhoneStockService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class AddToCartValidator implements Validator {

    @Resource
    private PhoneStockService phoneStockService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AddProductToCartForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddProductToCartForm addToCartForm = (AddProductToCartForm) target;
        Long quantity = extractQuantityFromForm(addToCartForm);
        if (quantity == null) {
            errors.reject("quantity.invalid", "The value must must be a number");
            return;
        }
        if (quantity < 1) {
            errors.reject("quantity.negative", "The value mus be greater than 0");
        }
        if (!phoneStockService.hasEnoughStock(addToCartForm.getPhoneId(), quantity)) {
            errors.reject("quantity.not.available", "Not enough stock for this product");
        }
    }
    private Long extractQuantityFromForm(AddProductToCartForm toCartForm) {
        try {
            return Long.parseLong(toCartForm.getQuantity());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
