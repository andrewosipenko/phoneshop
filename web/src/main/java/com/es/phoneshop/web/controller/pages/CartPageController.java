package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Autowired
    private CartService cartService;


    @GetMapping
    public String getCart(HttpSession httpSession, Model model) {
        model.addAttribute("cart", cartService.getCart(httpSession));
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart() {
        cartService.update(null, null);
    }
}
