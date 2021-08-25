package com.es.phoneshop.web.controller;

import com.es.core.exceptions.DataFormValidateException;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.pages.cart.AddPhoneResponse;
import com.es.phoneshop.web.controller.pages.cart.AddProductToCartForm;
import com.es.phoneshop.web.controller.pages.cart.AddToCartValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Resource
    private AddToCartValidator addToCartValidator;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addToCartValidator);
    }


    @PostMapping
    public @ResponseBody
    ResponseEntity<String> addPhone(@RequestBody @Validated AddProductToCartForm addToCartForm,
                                    BindingResult bindingResult) {
        AddPhoneResponse addPhoneResponse = new AddPhoneResponse();
        if (bindingResult.hasErrors()) {
            addPhoneResponse.setValid(false);
            addPhoneResponse.setErrors(bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getCode().toString()).collect(Collectors.toList()));
            throw new DataFormValidateException(bindingResult);
        } else {
            cartService.addPhone(addToCartForm.getPhoneId(), Long.valueOf(addToCartForm.getQuantity()));
            addPhoneResponse.setValid(true);
        }
        addPhoneResponse.setInfoCart(cartService.getInfoCart());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonBody = objectMapper.writeValueAsString(addPhoneResponse);
            return ResponseEntity.ok().body(jsonBody);
        } catch (JsonProcessingException e) {
            throw new DataFormValidateException(bindingResult);
        }
    }
}
