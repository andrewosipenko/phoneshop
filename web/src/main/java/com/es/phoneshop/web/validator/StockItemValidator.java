package com.es.phoneshop.web.validator;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.service.cart.CartService;
import com.es.core.service.stock.StockService;
import com.es.phoneshop.web.form.OrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StockItemValidator implements Validator {
    @Resource
    private StockService stockService;

    @Resource
    private CartService cartService;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        List<CartItem> cartItems = (List<CartItem>) o;

        for (int i = 0; i < cartItems.size(); i++) {
            long quantity = cartItems.get(i).getQuantity();
            long phoneId = cartItems.get(i).getPhone().getId();
            long maxStock = stockService.findPhoneStock(phoneId);
            if (maxStock < quantity) {
                errors.rejectValue("quantity",
                        String.valueOf(i),
                        new Long[] {phoneId},
                        "Sorry, we have only " + maxStock + " stock, was delete " + (quantity - maxStock) + " stock");
                cartService.removeMissingQuantity(cartItems.get(i));
            }
        }
    }
}
