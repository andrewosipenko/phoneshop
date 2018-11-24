package com.es.phoneshop.web.controller;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.services.CartService;
import com.es.core.model.cart.CartItem;
import com.es.phoneshop.web.services.CartItemValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    private static final String SUCCESS_MESSAGE = "{ \"message\": \"success\" }";
    private static final String INVALID_INPUT_MESSAGE = "{ \"message\": \"Quantity must be integer\" }";
    private static final String OUT_OF_STOCK_MESSAGE = "{ \"message\": \"Sorry, we haven't that amount of product\" }";
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
    public String addPhone(@RequestBody @Validated CartItem cartItem, Errors errors) {
        if (errors.hasErrors()) {
            return "{ \"message\": \"" + errors.getAllErrors().get(0).getCode() + "\" }";
        }
        try {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            return SUCCESS_MESSAGE;
        } catch (OutOfStockException exception) {
            return OUT_OF_STOCK_MESSAGE;
        }
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public String handle() {
        return INVALID_INPUT_MESSAGE;
    }
}
