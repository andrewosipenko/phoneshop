package com.es.phoneshop.web.validator;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.service.cart.CartService;
import com.es.core.service.stock.StockService;
import com.es.phoneshop.web.form.OrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.List;

@Service
@PropertySource("classpath:/message/errorMessageEN.properties")
public class StockItemValidator implements Validator {
    private static final String FIELD_QUANTITY = "quantity";

    @Value("${message.stockError}")
    private String messageStockError;

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
            long maxStock = stockService.findPhoneStock(phoneId).getStock();
            if (maxStock < quantity) {
                errors.rejectValue(FIELD_QUANTITY,
                        String.valueOf(i),
                        new Long[] {phoneId},
                        messageStockError + maxStock);
                cartService.removeMissingQuantity(cartItems.get(i));
            }
        }
    }
}
