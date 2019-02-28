package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.form.CartItemInfo;
import com.es.phoneshop.web.response.AddingToCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "**/ajaxCart", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AjaxCartController {
    private final static String STATUS_SUCCESS = "SUCCESS";
    private final static String STATUS_ERROR = "ERROR";

    @Resource
    private CartService cartService;

    @Autowired
    @Qualifier("cartItemInfoValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public AddingToCartResponse addPhone(@Validated @RequestBody CartItemInfo cartItemInfo, BindingResult bindingResult) {
        AddingToCartResponse response = new AddingToCartResponse();
        if (bindingResult.hasErrors()) {
            response.setErrors(bindingResult.getAllErrors());
            response.setStatus(STATUS_ERROR);
        } else {
            Cart cart = cartService.getCart();
            cartService.addCartItem(cart, cartItemInfo.getPhoneId(), cartItemInfo.getQuantity());
            response.setCountOfCartItems(cart.getCartItems().size());
            response.setTotalPrice(cart.getTotalPrice());
            response.setStatus(STATUS_SUCCESS);
        }
        return response;
    }
}
