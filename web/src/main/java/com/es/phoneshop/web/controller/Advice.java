package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.model.cart.CartStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.Resource;

@ControllerAdvice
public class Advice {
    @Resource
    private CartService cartService;

    private static final String ATTRIBUTE_CART_STATUS = "cartStatus";

    @ModelAttribute
    public void addCartStatusInModel(Model model) {
        CartStatus cartStatus = new CartStatus();
        cartStatus.setCountItems(cartService.getCountItems());
        cartStatus.setPrice(cartService.getPrice());
        model.addAttribute(ATTRIBUTE_CART_STATUS, cartStatus);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "errors/page404";
    }
}
