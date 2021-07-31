package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private final CartService cartService;

    @Autowired
    public CartPageController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public void getCart() {
        cartService.getCart();
    }

    @PutMapping
    public void updateCart() {
        cartService.update(null);
    }
}
