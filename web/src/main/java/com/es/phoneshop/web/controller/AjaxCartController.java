package com.es.phoneshop.web.controller;

import com.es.core.exceptions.DataFormValidateException;
import com.es.core.model.cart.InfoCart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.pages.cart.AddPhoneResponse;
import com.es.phoneshop.web.controller.pages.cart.AddProductToCartForm;
import com.es.phoneshop.web.controller.pages.cart.AddToCartValidator;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Resource
    private AddToCartValidator addToCartValidator;

    @Resource
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addToCartValidator);
    }


    @PostMapping
    public @ResponseBody
    AddPhoneResponse addPhone(@RequestBody @Validated AddProductToCartForm addToCartForm, BindingResult bindingResult) {
        AddPhoneResponse addPhoneResponse = new AddPhoneResponse();
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors("quantity").stream()
                    .map((fe) -> messageSource.getMessage(fe, null)).collect(Collectors.toList());
            addPhoneResponse.setValid(false);
            addPhoneResponse.setErrors(errors);
        } else {
            cartService.addPhone(addToCartForm.getPhoneId(), Long.valueOf(addToCartForm.getQuantity()));
            addPhoneResponse.setValid(true);
        }
        addPhoneResponse.setInfoCart(cartService.getInfoCart());
        return addPhoneResponse;
    }
}
