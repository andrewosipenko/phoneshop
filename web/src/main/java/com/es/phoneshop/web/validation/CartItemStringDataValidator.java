package com.es.phoneshop.web.validation;

import com.es.core.dao.StockDao;
import com.es.core.model.cart.CartItemStringData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class CartItemStringDataValidator implements Validator {
    private static final String QUANTITY = "quantityString";
    private static final String PHONE_ID = "phoneIdString";
    private static final String PHONE_ID_IS_EMPTY = "PhoneId is empty!";
    private static final String QUANTITY_IS_EMPTY = "Quantity is empty!";
    private static final String QUANTITY_LESS_EQUAL_ZERO = "Quantity <= 0 !";
    private static final String QUANTITY_NOT_ENOUGH = "Not enough quantity!";
    private static final String NOT_NUMBER = "Not a number!";

    private StockDao stockDao;

    @Autowired
    public CartItemStringDataValidator(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemStringDataValidator.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        CartItemStringData cartItemStringData = (CartItemStringData) object;
        if (StringUtils.isBlank(cartItemStringData.getPhoneIdString())) {
            errors.rejectValue(PHONE_ID, PHONE_ID_IS_EMPTY);
        }
        if (StringUtils.isBlank(cartItemStringData.getQuantityString())) {
            errors.rejectValue(QUANTITY, QUANTITY_IS_EMPTY);
        }

        try {
            long quantity = Long.parseLong(cartItemStringData.getQuantityString());
            long phoneId = Long.parseLong(cartItemStringData.getPhoneIdString());
            if (quantity <= 0) {
                errors.rejectValue(QUANTITY, QUANTITY_LESS_EQUAL_ZERO);
            }
            int available = stockDao.getStockById(phoneId).get().getStock() -
                    stockDao.getStockById(phoneId).get().getReserved();
            if (quantity > available) {
                errors.rejectValue(QUANTITY, QUANTITY_NOT_ENOUGH);
            }
        } catch (NumberFormatException exception) {
            errors.rejectValue(QUANTITY, NOT_NUMBER);
        }

    }
}
