package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phone.ItemNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.phoneshop.web.controller.cart.CartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ExceptionController {

    @Autowired
    private CartView cartView;

    @Autowired
    private Cart cart;


    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView handleItemNotFoundException(ItemNotFoundException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", ex.getMessage());
        model.addObject("statusCode", 404);
        return model;
    }


    @ModelAttribute
    public void handleRequest(HttpServletRequest request, Model model) {
        model.addAttribute("cartView", cartView);
        model.addAttribute("cart", cart);
    }
}



