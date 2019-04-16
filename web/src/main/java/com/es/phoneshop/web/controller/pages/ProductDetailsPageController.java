package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_PHONE = "phone";
    private final String PAGE_PRODUCT_DETAILS = "productDetails";

    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;

    @GetMapping(value = "/phoneId={phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        Phone phone = phoneService.getPhone(phoneId).get();
        model.addAttribute(ATTRIBUTE_PHONE, phone);
        return PAGE_PRODUCT_DETAILS;
    }
}
