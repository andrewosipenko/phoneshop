package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.order.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        cartService.addPhone(phoneId, quantity);
        modelAndView.addObject("cartItemsAmount", cartService.getCart().getCartItemsAmount());
        modelAndView.addObject("cartItemsPrice", cartService.getCart().getCartItemsPrice());
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }
}
