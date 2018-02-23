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

import static com.es.phoneshop.web.constant.ControllerMapping.AJAX_CART_CONTROLLER;

@RestController
@RequestMapping(AJAX_CART_CONTROLLER)
public class AjaxCartController {

    @Resource
    private CartService cartService;

    private final static String ERROR_MESSAGE_WRONG_FORMAT = "Wrong format";


    /**
     * @throws InvalidFormatException - can't parse values from request;
     * @throws PhoneNotFoundException - addPhone throws, if phone not found.
     * @throws IllegalStateException  - try to add a phone without price
     */
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

    @ExceptionHandler({InvalidFormatException.class,
            PhoneNotFoundException.class,
            IllegalStateException.class})
    public ResponseEntity<CartStatus> handleNumberFormatException() {
        return createResponse(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<CartStatus> createResponse(HttpStatus status) {

        CartStatus cartStatus = new CartStatus();

        if (status == HttpStatus.OK) {
            cartStatus.setCartCost(cartService.getCart().getCost());
            cartStatus.setPhonesCount(cartService.getCart().getCountItems());
        } else {
            cartStatus.setErrorMessage(ERROR_MESSAGE_WRONG_FORMAT);
        }

        return ResponseEntity.status(status).body(cartStatus);
    }
}

