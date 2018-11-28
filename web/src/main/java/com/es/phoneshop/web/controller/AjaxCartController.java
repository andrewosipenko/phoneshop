package com.es.phoneshop.web.controller;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.services.cart.CartService;
import com.es.core.model.cart.CartItem;
import com.es.phoneshop.web.services.CartItemValidator;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    private static final String SUCCESS_MESSAGE = "success";
    private static final String INVALID_INPUT_MESSAGE = "Quantity must be integer";
    private static final String OUT_OF_STOCK_MESSAGE = "Sorry, we haven't that amount of product";

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
    public Map<String, Object> addPhone(@RequestBody @Validated CartItem cartItem, Errors errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("cartItemsAmount", cartService.getQuantityOfProducts()+"");
        if (errors.hasErrors()) {
            response.put("message", errors.getAllErrors().get(0).getDefaultMessage());
            return response;
        }
        try {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            response.put("cartItemsAmount", cartService.getQuantityOfProducts());
            response.put("message", SUCCESS_MESSAGE);
            return response;
        } catch (OutOfStockException exception) {
            response.put("message", OUT_OF_STOCK_MESSAGE);
            return response;
        }
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Map<String, Object> handle() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", INVALID_INPUT_MESSAGE);
        return response;
    }
}
