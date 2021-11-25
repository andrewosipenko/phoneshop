package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public void getCart(HttpSession session) {
        cartService.getCart(session);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart() {
        cartService.update(null);
    }
}
