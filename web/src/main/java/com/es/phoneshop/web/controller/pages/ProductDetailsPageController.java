package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;

    @GetMapping(value = "/phoneId={phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model){
        Phone phone = phoneService.getPhone(phoneId).get();
        model.addAttribute("phone", phone);
        return "productDetails";
    }
}
