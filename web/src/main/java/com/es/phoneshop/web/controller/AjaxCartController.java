package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.cart.cost.CostService;
import com.es.phoneshop.web.model.cart.CartPhone;
import com.es.phoneshop.web.model.cart.CartStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Resource
    private CostService costService;

    @Resource
    private MessageSource messageSource;

    @PostMapping
    public ResponseEntity addPhone(@Valid CartPhone cartPhone, BindingResult bindingResult, Locale locale) {
        CartStatus cartStatus = new CartStatus();
        HttpStatus status;

        if (bindingResult.hasErrors()) {
            String errorMessage = messageSource.getMessage("wrongFormat", null, locale);
            cartStatus.setErrorMessage(errorMessage);
            status = HttpStatus.BAD_REQUEST;
        } else {
            cartService.addPhone(cartPhone.getPhoneId(), cartPhone.getQuantity());
            cartStatus.setCountItems(cartService.getCountItems());
            cartStatus.setPrice(costService.getCost(cartService.getCart()));
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(cartStatus);
    }
}
