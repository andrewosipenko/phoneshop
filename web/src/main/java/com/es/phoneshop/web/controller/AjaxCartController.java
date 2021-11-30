package com.es.phoneshop.web.controller;

import com.es.core.model.cart.CartService;
import com.es.phoneshop.web.controller.pages.dto.CartAddForm;
import com.es.phoneshop.web.controller.pages.dto.CartInfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    public static final String QUANTITY = "quantity";
    public static final String ERROR = "ERROR";
    public static final String ADDED_TO_CART = "Added to cart!";
    public static final String SUCCESS = "SUCCESS";
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    CartInfoResponse addPhone(@Valid CartAddForm cartAddForm,
                              BindingResult result) {
        CartInfoResponse cartInfoResponse = new CartInfoResponse();
        if (result.hasErrors()) {
            cartInfoResponse.setMessage(result.getFieldError(QUANTITY).toString());
            cartInfoResponse.setStatus(ERROR);
            return cartInfoResponse;
        }
        cartService.addPhone(cartAddForm.getPhoneId(), cartAddForm.getQuantity());
        cartInfoResponse.setMessage(ADDED_TO_CART);
        cartInfoResponse.setStatus(SUCCESS);
        return cartInfoResponse;
    }
}
