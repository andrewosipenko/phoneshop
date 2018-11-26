package com.es.phoneshop.web.controller;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.services.cart.CartService;
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
    private static final String SUCCESS_MESSAGE = "success";
    private static final String INVALID_INPUT_MESSAGE = "Quantity must be integer";
    private static final String OUT_OF_STOCK_MESSAGE = "Sorry, we haven't that amount of product";
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
        String response = makeJsonStringToResponse("cartItemsAmount", cartService.getQuantityOfProducts()+"");
        if (errors.hasErrors()) {
            return makeJsonStringToResponse("message", errors.getAllErrors().get(0).getDefaultMessage(), response);
        }
        try {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            response = rewriteKeyOfJsonString("cartItemsAmount", cartService.getQuantityOfProducts()+"", response);
            return makeJsonStringToResponse("message", SUCCESS_MESSAGE, response);
        } catch (OutOfStockException exception) {
            return makeJsonStringToResponse("message", OUT_OF_STOCK_MESSAGE, response);
        }
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public String handle() {
        return makeJsonStringToResponse("message", INVALID_INPUT_MESSAGE);
    }

    private String makeJsonStringToResponse(String key, String value, String jsonString) {
        StringBuilder stringBuilder = new StringBuilder(jsonString);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(", \"");
        stringBuilder.append(key);
        stringBuilder.append("\": \"");
        stringBuilder.append(value);
        stringBuilder.append("\" }");
        return stringBuilder.toString();
    }

    private String makeJsonStringToResponse(String key, String value) {
        String result = makeJsonStringToResponse(key, value, "{}");
        return result.replace(",", "");
    }

    private String rewriteKeyOfJsonString(String key, String value, String jsonString) {
        StringBuilder stringBuilder = new StringBuilder(jsonString);
        int indexOfKey = jsonString.indexOf("{ \""+key);
        if (indexOfKey < 0) {
            indexOfKey = jsonString.lastIndexOf(", \""+key);
        }
        int startIndexOfValue = indexOfKey + 7 + key.length();
        int endIndexOfValue = stringBuilder.indexOf("\"", startIndexOfValue);
        stringBuilder.replace(startIndexOfValue, endIndexOfValue, value);
        return stringBuilder.toString();
    }
}
