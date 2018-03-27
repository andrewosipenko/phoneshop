package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.phoneshop.web.bean.cart.CartInfo;
import com.es.phoneshop.web.bean.cart.CartItem;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

import static com.es.phoneshop.web.controller.constants.AjaxConstants.WRONG_INPUT;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CartInfo> addPhone(@RequestBody @Valid CartItem cartItem, BindingResult bindingResult)
            throws PhoneNotFoundException {
        if (!bindingResult.hasErrors()) {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            Cart cart = cartService.getCart();
            CartInfo cartInfo = new CartInfo(cart.getItemsCount(), cart.getCost());
            return ResponseEntity.ok(cartInfo);
        }
        CartInfo cartInfo = new CartInfo(WRONG_INPUT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cartInfo);
    }
}
