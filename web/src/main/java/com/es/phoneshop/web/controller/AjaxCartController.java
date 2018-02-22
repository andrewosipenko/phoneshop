package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.exception.PhoneNotFoundException;
import com.es.phoneshop.web.bean.cart.CartAddPhoneInfo;
import com.es.phoneshop.web.bean.cart.CartStatus;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    private final static String ERROR_MESSAGE_WRONG_FORMAT = "Wrong format";

    @PostMapping
    public ResponseEntity<CartStatus> addPhone(@RequestBody @Valid CartAddPhoneInfo cartInfo,
                                               BindingResult bindingResult) throws PhoneNotFoundException {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (!bindingResult.hasErrors()) {
            cartService.addPhone(cartInfo.getPhoneId(), cartInfo.getQuantity());
            status = HttpStatus.OK;
        }

        return createResponse(status);
    }


    /**
     *  @throws InvalidFormatException - can't parse values from request;
     *  @throws PhoneNotFoundException - addPhone throw, because phone not found.
     */
    @ExceptionHandler({InvalidFormatException.class, PhoneNotFoundException.class})
    public ResponseEntity<CartStatus> handleNumberFormatException() {
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

