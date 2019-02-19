package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "**/ajaxCart", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AjaxCartController {
    private final static String CART_ATTRIBUTE = "cart";

    @Resource
    private CartService cartService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Cart addPhone(@RequestBody Long phoneId,
                         @RequestBody Long quantity, Model model) {
        Cart cart = cartService.getCart();
        cartService.addCartItem(cart, phoneId, quantity);

        /*if (bindingResult.hasErrors()) {
            System.out.println("AFIJBNFBJ");
        }*/

       /* if (bindingResult1.hasErrors()) {
            System.out.println("111111111");
        }*/
        //cartService.addPhone(phoneId, quantity);
        return cart;
    }
}
