package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.beans.QuantityWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/ajaxCart")
@Validated
public class AjaxCartController {

    @Resource
    private CartService cartService;

    private ObjectMapper jsonMapper = new ObjectMapper();


    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, params = {"quantity"})
    public ResponseEntity<String> addPhone(
            @RequestParam Long phoneId,
            @Valid QuantityWrapper quantity,
            BindingResult bindingResult) {
        String message;
        if(bindingResult.hasErrors()){
            message = bindingResult.getFieldError().getDefaultMessage();
        } else {
            cartService.addPhone(phoneId, quantity.getValue());
            message = "success";
        }
        Map<String, String> properties = new HashMap<>();
        properties.put("message", message);

        properties.put("cartSubTotal", cartService.getCartSubTotal().toPlainString());
        properties.put("cartQuantity", cartService.getProductsQuantity().toString());

        return getJsonEntity(properties);
    }

    private ResponseEntity<String> getJsonEntity(Map<String, String> map){
        String json;
        HttpStatus status = HttpStatus.OK;
        try {
            json = jsonMapper.writeValueAsString(map);
        } catch (JsonProcessingException e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            json = "";
        }
        return new ResponseEntity<>(json, status);
    }
}
