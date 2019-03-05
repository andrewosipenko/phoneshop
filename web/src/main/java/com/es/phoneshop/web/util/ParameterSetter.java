package com.es.phoneshop.web.util;

import com.es.core.model.cart.Cart;
import org.springframework.ui.Model;

public final class ParameterSetter {
    private final static String TOTAL_PRICE = "totalPrice";
    private final static String COUNT_OF_CART_ITEM = "countOfCartItems";

    private ParameterSetter() {

    }

    public static void setCartParameters(Cart cart, Model model) {
        model.addAttribute(COUNT_OF_CART_ITEM, cart.getCartItems().size());
        model.addAttribute(TOTAL_PRICE, cart.getTotalPrice());
    }


}
