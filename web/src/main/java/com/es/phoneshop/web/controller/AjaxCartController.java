package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.phoneshop.web.exception.InvalidCartItemException;
import com.es.phoneshop.web.bean.cart.CartInfo;
import com.es.phoneshop.web.bean.cart.CartItemInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody CartInfo addPhone(@Valid CartItemInfo cartItemInfo, BindingResult bindingResult) throws InvalidCartItemException,
            PhoneNotFoundException {
        if (bindingResult.hasFieldErrors())
            throw new InvalidCartItemException();
        cartService.addPhone(cartItemInfo.getPhoneId(), cartItemInfo.getQuantity());
        Cart cart = cartService.getCart();
        return new CartInfo(cart.getItemsCount(), cart.getCost());
    }
}
