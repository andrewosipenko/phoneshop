package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneService phoneService;

    private static final String ATTRIBUTE_PHONE = "phone";

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable long id,
                                     Model model) {
        Optional<Phone> phone = phoneService.get(id);
        if (phone.isPresent()) {
            model.addAttribute(ATTRIBUTE_PHONE, phone.get());
            return "productDetails";
        } else {
            return "errors/page404";
        }
    }
}
