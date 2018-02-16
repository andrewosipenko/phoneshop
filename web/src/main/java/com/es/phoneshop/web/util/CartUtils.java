package com.es.phoneshop.web.util;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.model.cart.CartStatus;
import org.springframework.ui.Model;

public final class CartUtils {

    public static void addCartStatusInModel(CartService cartService, Model model, String attribute) {
        CartStatus cartStatus = new CartStatus();
        cartStatus.setCountItems(cartService.getCountItems());
        cartStatus.setPrice(cartService.getPrice());
        model.addAttribute(attribute, cartStatus);
    }
}
