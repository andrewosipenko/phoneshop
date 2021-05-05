package com.es.core.validator;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Stock;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class CartValidator implements Validator {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartForm.class.equals(aClass);
    }

    @Override
    public void validate(Object objectForValidating, Errors errors) {
        CartForm cartForm = (CartForm) objectForValidating;
        for (int i = 0; i < cartForm.getPhoneIds().size(); i++) {
            long quantity;
            try {
                quantity = Long.parseLong(cartForm.getQuantities().get(i));
                if (quantity <= 0) {
                    errors.reject(cartForm.getPhoneIds().get(i), "Enter number more than 0");
                }
                Long phoneId = Long.parseLong(cartForm.getPhoneIds().get(i));
                checkStock(phoneId, quantity, errors);
            } catch (NumberFormatException e) {
                errors.reject(cartForm.getPhoneIds().get(i), "Enter integer number");
            }
        }
    }

    private void checkStock(Long phoneId, Long quantity, Errors errors) {
        phoneDao.get(phoneId).ifPresent(phone -> {
            Stock stock = phoneDao.findStock(phone);
            if (stock.getStock() < quantity) {
                errors.reject(phoneId.toString(), "Out of stock. Available: " + stock.getStock());
            }
        });
    }
}
