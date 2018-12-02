package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.services.cart.CartService;
import com.es.core.services.cart.TotalPriceService;
import com.es.core.services.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneService phoneService;
    @Resource
    private CartService cartService;
    @Resource
    private TotalPriceService totalPriceService;

    @GetMapping()
    public String showProductList(Long phoneId, Model model) {
        model.addAttribute("cartItemsAmount", cartService.getQuantityOfProducts());
        model.addAttribute("cartItemsPrice", totalPriceService.getTotalPriceOfProducts());
        if (phoneId != null) {
            Optional<Phone> phone = phoneService.get(phoneId);
            phone.ifPresent(phone1 -> model.addAttribute("phone", phone1));
        } else {
            return "redirect:/404page";
        }
        return "productDetails";
    }
}
