package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    private static final String PHONE_ID_PARAM = "phoneId";
    private static final String QUANTITY_PARAM = "quantity";
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public String addPhone(@RequestParam Map<String, String> form, HttpSession session) {
        Cart cart = cartService.getCart(session);
        cartService.addPhone(cartService.getCart(session), Long.parseLong(form.get(PHONE_ID_PARAM)),
                Long.parseLong(form.get(QUANTITY_PARAM)));
        return cart.getTotalQuantity() + " items " + cart.getTotalCost();
    }
}
