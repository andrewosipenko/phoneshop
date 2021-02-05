package com.es.core.dto.validation;

import com.es.core.dto.CartInfoDTO;
import com.es.core.model.PhoneModel;
import com.es.service.PhoneService;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<ValidQuantityAndStock, CartInfoDTO> {

    @Resource
    private PhoneService phoneService;

    @Override
    public void initialize(ValidQuantityAndStock cartInfoValidator) {
    }

    @Override
    public boolean isValid(CartInfoDTO cartInfoDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (cartInfoDTO.getQuantityToAdd().matches("[0-9]+") && !cartInfoDTO.getQuantityToAdd().isEmpty()) {
            PhoneModel phoneModel = phoneService.get(cartInfoDTO.getProductId());
            if (Long.valueOf(cartInfoDTO.getQuantityToAdd()) > phoneModel.getStock()) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Available stock is : " + phoneModel.getStock() + " items ")
                        .addNode("quantityToAdd")
                        .addConstraintViolation();
                return false;
            }
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("invalid input format")
                    .addNode("quantityToAdd")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
