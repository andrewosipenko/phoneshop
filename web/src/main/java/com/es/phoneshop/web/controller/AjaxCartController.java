package com.es.phoneshop.web.controller;

import com.es.phoneshop.core.cart.model.Cart;
import com.es.phoneshop.core.cart.service.CartService;
import com.es.phoneshop.core.cart.throwable.NoStockFoundException;
import com.es.phoneshop.core.cart.throwable.NoSuchPhoneException;
import com.es.phoneshop.core.cart.throwable.TooBigQuantityException;
import com.es.phoneshop.web.controller.form.AddToCartForm;
import com.es.phoneshop.web.controller.throwable.IncorrectFormFormatException;
import com.es.phoneshop.web.controller.throwable.InternalException;
import com.es.phoneshop.web.controller.util.CartStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    @Value("${error.incorrect.quantity}")
    private String INCORRECT_QUANTITY_MESSAGE;
    @Value("${error.tooBig.quantity}")
    private String TOO_BIG_QUANTITY_MESSAGE;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody CartStatus getCartStatus() {
        Cart cart = cartService.getCart();
        return new CartStatus(cart.getPhonesTotal(), cart.getSubtotal());
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody CartStatus addPhone(@ModelAttribute("addToCartForm") @Valid AddToCartForm addToCartForm, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors())
            throw new IncorrectFormFormatException();
        try {
            cartService.add(addToCartForm.getPhoneId(), addToCartForm.getQuantity());
        } catch (NoSuchPhoneException | NoStockFoundException e) {
            throw new InternalException();
        }
        return getCartStatus();
    }

    @ExceptionHandler(IncorrectFormFormatException.class)
    private @ResponseBody @ResponseStatus(HttpStatus.BAD_REQUEST) String handleIncorrectFormFormat() {
        return INCORRECT_QUANTITY_MESSAGE;
    }

    @ExceptionHandler(TooBigQuantityException.class)
    private @ResponseBody @ResponseStatus(HttpStatus.BAD_REQUEST) String handleTooBigQuantity() {
        return TOO_BIG_QUANTITY_MESSAGE;
    }
}
