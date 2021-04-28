package com.es.phoneshop.web.controller;

import com.es.core.model.cart.CartItem;
import com.es.core.service.CartService;
import com.es.core.validator.CartItemForm;
import com.es.core.validator.CartItemValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    @Resource
    private CartItemValidator validator;
    private static final String ADDED_TO_CART = "Added to cart successfully";

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> addPhone(@RequestBody CartItemForm cartItemForm, BindingResult bindingResult) {
        validator.validate(cartItemForm, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorString = bindingResult.getAllErrors().get(0).getCode();
            return ResponseEntity.badRequest().body(errorString);
        }
        CartItem cartItem = new CartItem(
                Long.parseLong(cartItemForm.getPhoneId()),
                Long.parseLong(cartItemForm.getQuantity()));
        cartService.addPhone(cartItem);
        return ResponseEntity.ok(ADDED_TO_CART);
    }
}
