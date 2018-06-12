package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.beans.AddPhoneResponse;
import com.es.phoneshop.web.controller.beans.QuantityForm;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/ajaxCart")
@Validated
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Resource
    private MessageSource messageSource;

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AddPhoneResponse addPhone(
            @Valid QuantityForm quantityForm,
            BindingResult bindingResult) {
        AddPhoneResponse addPhoneResponse = new AddPhoneResponse();
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors("quantity").stream()
                    .map((fe)->messageSource.getMessage(fe, null)).collect(Collectors.toList());
            addPhoneResponse.setValid(false);
            addPhoneResponse.setErrors(errors);
        } else {
            cartService.addPhone(quantityForm.getPhoneId(), quantityForm.getQuantity());
            addPhoneResponse.setValid(true);
        }
        addPhoneResponse.setCartQuantity(cartService.getProductsQuantity());
        addPhoneResponse.setCartSubTotal(cartService.getCartSubTotal());

        return addPhoneResponse;
    }

}
