package com.es.phoneshop.web.controller.pages.cart;


import com.es.core.service.phone.PhoneStockService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class UpdateCartValidator implements Validator {

    private static final String CART_ITEM_S = "cartItems['%s']";

    @Resource
    private PhoneStockService stockService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CartItemsForUpdate.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        CartItemsForUpdate cartItemsUpdateForm = (CartItemsForUpdate) object;

        validateQuantities(cartItemsUpdateForm, errors);
    }

    private void validateQuantities(CartItemsForUpdate cartItemsUpdateForm, Errors errors) {
        cartItemsUpdateForm.getCartItems().entrySet().forEach(longStringEntry -> validateQuantity(longStringEntry, errors));
    }

    private void validateQuantity(Map.Entry<Long, String> cartItemEntry, Errors errors) {
        Long itemKey = cartItemEntry.getKey();
        Long quantity = tryExtractQuantityFromForm(cartItemEntry.getValue());
        if (quantity == null) {
            errors.rejectValue(String.format(CART_ITEM_S, itemKey), "quantity.invalid", "The value must must be a number");
            return;
        }
        if (quantity <= 0) {
            errors.rejectValue(String.format(CART_ITEM_S, itemKey), "quantity.negative", "The value must be greater than 0");
            return;
        }
        if (!stockService.hasEnoughStock(itemKey, quantity)) {
            errors.rejectValue(String.format(CART_ITEM_S, itemKey), "quantity.not.available", "Not enough stock for this product");
        }
    }

    private Long tryExtractQuantityFromForm(String quantityString) {
        try {
            return Long.parseLong(quantityString);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}