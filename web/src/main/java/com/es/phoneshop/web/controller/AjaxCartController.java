package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        cartService.addPhone(phoneId, quantity);
    }
}
