package com.es.phoneshop.web.controller;

import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.cart.CartItemStringData;
import com.es.core.model.cart.CartStatus;
import com.es.core.service.CartService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ajaxCart", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AjaxCartController {
    private static final String SUCCESS = "Added successfully!";
    private static final String ERROR = "Error!";

    @Resource
    private CartService cartService;

    private final Validator cartItemStringDataValidator;

    public AjaxCartController(@Qualifier("cartItemStringDataValidator") Validator cartItemStringDataValidator) {
        this.cartItemStringDataValidator = cartItemStringDataValidator;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    CartStatus addPhone(@RequestBody @Valid CartItemStringData cartItemStringData, BindingResult bindingResult) throws Exception {
        cartItemStringDataValidator.validate(cartItemStringData, bindingResult);
        if (!bindingResult.hasErrors()) {
            Long[] cartItemLongData = getLongValues(cartItemStringData);
            cartService.addPhone(cartItemLongData[0], cartItemLongData[1]);
            return createCartStatus(true, null, SUCCESS);
        }
        String error = createErrorMessage(bindingResult);
        return createCartStatus(false, error, null);
    }

    @ExceptionHandler({InvalidFormatException.class,
            PhoneNotFoundException.class,
            IllegalStateException.class})
    public @ResponseBody
    CartStatus handleException() {
        return createCartStatus(false, ERROR, null);
    }


    private String createErrorMessage(BindingResult bindingResult) {
        String errorMessage = "";
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                errorMessage += fieldError.getCode() + '\n';
            }
        }
        return errorMessage;
    }

    private CartStatus createCartStatus(boolean valid, String error, String success) {
        CartStatus cartStatus = new CartStatus();
        cartStatus.setTotalQuantity(cartService.getCartTotalQuantity());
        cartStatus.setTotalPrice(cartService.getCartTotalPrice());
        if (valid == true) {
            cartStatus.setValid(true);
            cartStatus.setSuccessMessage(success);
        } else {
            cartStatus.setValid(false);
            cartStatus.setErrorMessage(error);
        }
        return cartStatus;
    }

    private Long[] getLongValues(CartItemStringData cartItemStringData) {
        Long[] longValues = new Long[2];
        longValues[0] = Long.valueOf(cartItemStringData.getPhoneIdString().trim());
        longValues[1] = Long.valueOf(cartItemStringData.getQuantityString().trim());
        return longValues;
    }

}
