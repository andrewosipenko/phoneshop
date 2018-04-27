package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.web.controller.service.phone.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Autowired
    private PhoneService phoneService;

    @GetMapping(value = "/phoneId={phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model){
        model.addAttribute("phone", phoneService.getPhone(phoneId));
        return "productDetails";
    }
}
