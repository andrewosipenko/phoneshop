package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/ajaxCart")
@Validated
public class AjaxCartController {
    private static final String ERROR_MESSAGE = "error";
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public String addPhone(@RequestParam long phoneId, @RequestParam String quantity,
                           HttpSession session) {
        if (!quantity.matches("[0-9]+")) {
            return ERROR_MESSAGE;
        }
        Cart cart = cartService.getCart(session);
        cartService.addPhone(cart, phoneId, Long.parseLong(quantity));
        return cart.getTotalQuantity() + " items " + cart.getTotalCost();
    }
}
