package com.es.phoneshop.web.controller;

import com.es.core.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    private final CartService cartService;

    @Autowired
    public AjaxCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public void addPhone(Long phoneId, Long quantity) {
        cartService.addPhone(phoneId, quantity);
    }
}
