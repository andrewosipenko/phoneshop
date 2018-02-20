package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.phoneshop.web.bean.CartAddPhoneInfo;
import com.es.phoneshop.web.bean.CartStatus;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    private final static String ERROR_MESSAGE_WRONG_FORMAT = "Wrong format";

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CartStatus> addPhone(@RequestBody @Valid CartAddPhoneInfo cartInfo, BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (!bindingResult.hasErrors()) {
            try {
                cartService.addPhone(cartInfo.getPhoneId(), cartInfo.getQuantity());
                status = HttpStatus.OK;
            } catch (PhoneNotFoundException e) {
                status = HttpStatus.BAD_REQUEST;
            }
        }

        return createResponse(status);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<CartStatus> handleNumberFormatException(InvalidFormatException e) {
        return createResponse(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<CartStatus> createResponse(HttpStatus status) {

        CartStatus cartStatus = new CartStatus();

        if (status == HttpStatus.OK) {
            cartStatus.setCartCost(cartService.getCartCost());
            cartStatus.setPhonesCount(cartService.getPhonesCountInCart());
        } else {
            cartStatus.setErrorMessage(ERROR_MESSAGE_WRONG_FORMAT);
        }

        return ResponseEntity.status(status).body(cartStatus);
    }
}

