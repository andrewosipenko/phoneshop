package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Autowired
    private CartService<HttpSession> cartService;

    @RequestMapping(method = RequestMethod.GET)
    public void getCart(HttpSession httpSession) {
      cartService.getCart(httpSession);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart() {
        cartService.update(null, null);
    }
}
