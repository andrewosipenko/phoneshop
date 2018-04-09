package com.es.phoneshop.web.controller;

import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.model.CartStatus;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.web.controller.throwable.IncorrectFormFormatException;
import com.es.phoneshop.web.controller.util.AddToCartForm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody CartStatus getCartStatus() {
        return cartService.getCartStatus();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody CartStatus addPhone(@Valid AddToCartForm form, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors())
            throw new IncorrectFormFormatException();
        cartService.addPhone(form.getPhoneId(), form.getQuantity());
        return cartService.getCartStatus();
    }

    @ExceptionHandler(IncorrectFormFormatException.class)
    private @ResponseBody @ResponseStatus(HttpStatus.BAD_REQUEST) String handleIncorrectFormFormat() {
        return "Quantity must be positive integer value";
    }

    @ExceptionHandler(TooBigQuantityException.class)
    private @ResponseBody @ResponseStatus(HttpStatus.BAD_REQUEST) String handleTooBigQuantity() {
        return "Quantity value is too big";
    }

    @ExceptionHandler(NoSuchPhoneException.class)
    private @ResponseBody @ResponseStatus(HttpStatus.NOT_FOUND) String handleNoSuchPhone() {
        return "No such phone";
    }

    @ExceptionHandler(NoStockFoundException.class)
    private @ResponseBody @ResponseStatus(HttpStatus.NOT_FOUND) String handleNoStockFound() {
        return "No stock info found";
    }
}
