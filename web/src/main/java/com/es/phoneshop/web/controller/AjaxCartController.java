package com.es.phoneshop.web.controller;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.services.CartService;
import com.es.core.model.cart.CartItem;
import com.es.phoneshop.web.services.CartItemValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Lazy
    @Resource
    private CartService cartService;
    @Resource
    private CartItemValidator cartItemValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(cartItemValidator);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addPhone(@RequestBody @Validated CartItem cartItem) {
        try {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
        } catch (OutOfStockException exception) {
            System.out.println("Fail adding to cart");
        }
    }
}
