package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    public PhoneService phoneService;

    @Resource
    public CartService cartService;


    @GetMapping("/{phoneId:[1-9][0-9]*}")
    public String showPhoneDetails(
            @PathVariable Long phoneId,
            Model model){
        model.addAttribute("phone", phoneService.getById(phoneId));
        return "phoneDetails";
    }

    @ModelAttribute
    public void addCart(Model model){
        model.addAttribute("cartSubTotal", cartService.getCartSubTotal());
        model.addAttribute("cartQuantity", cartService.getProductsQuantity());
    }
}
