package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.beans.AddPhoneResponse;
import com.es.phoneshop.web.controller.beans.QuantityForm;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/ajaxCart")
@Validated
public class AjaxCartController {

    @Resource
    private CartService cartService;


    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AddPhoneResponse addPhone(
            @Valid QuantityForm quantityForm,
            BindingResult bindingResult) {
        AddPhoneResponse addPhoneResponse = new AddPhoneResponse();
        if(bindingResult.hasErrors()){
            addPhoneResponse.setMessage(bindingResult.getFieldError().getDefaultMessage());
        } else {
            cartService.addPhone(quantityForm.getPhoneId(), Long.valueOf(quantityForm.getQuantity()));
            addPhoneResponse.setMessage("success");
        }
        addPhoneResponse.setCartQuantity(cartService.getProductsQuantity());
        addPhoneResponse.setCartSubTotal(cartService.getCartSubTotal());

        return addPhoneResponse;
    }

}
