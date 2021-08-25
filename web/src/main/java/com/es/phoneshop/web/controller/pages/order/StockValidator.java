package com.es.phoneshop.web.controller.pages.order;

import com.es.core.model.cart.Cart;
import com.es.core.service.phone.PhoneStockService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class StockValidator implements Validator {

    @Resource
    private PhoneStockService phoneStockService;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Cart cart = (Cart) o;
        boolean hasEnoughStockInTheCart = cart.getCartItems()
                .removeIf(cartItem ->
                        !phoneStockService.hasEnoughStock(cartItem.getPhone().getId(), cartItem.getQuantity()));
        if (hasEnoughStockInTheCart) {
            errors.reject("stock.unavailable", "Some items in the cart are not available");
        }
    }
}
