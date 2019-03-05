package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import com.es.phoneshop.web.util.ParameterSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails/{phoneId}")
public class ProductDetailsPageController {
    private final static String DETAILS_PAGE = "productDetails";
    private final static String PHONE_PARAMETER = "phone";

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showDetailsPage(@PathVariable long phoneId, Model model) {
        Phone phone = phoneService.get(phoneId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute(PHONE_PARAMETER, phone);
        Cart cart = cartService.getCart();
        ParameterSetter.setCartParameters(cart, model);
        return DETAILS_PAGE;
    }
}
