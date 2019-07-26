package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.validator.quantity.ValidatedQuantity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String addPhone(@RequestParam Long phoneId, @Valid ValidatedQuantity validatedQuantity,
                    BindingResult result) {
        if (result.hasErrors()) {
            return "wrong format";
        }
        cartService.addPhone(phoneId, Long.parseLong(validatedQuantity.getQuantity()));
        return "added!";
    }

}
