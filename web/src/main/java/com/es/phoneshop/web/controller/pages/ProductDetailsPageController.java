package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.phone.service.PhoneService;
import com.es.phoneshop.web.controller.form.AddToCartForm;
import com.es.phoneshop.web.controller.throwable.PhoneNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/productDetails/{phoneId:[1-9][\\d]{0,17}}")
public class ProductDetailsPageController {
    @Resource
    private PhoneService phoneService;

    @ModelAttribute("addToCartForm")
    private AddToCartForm makeAddToCartForm() {
        return new AddToCartForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductDetails(Model model, @PathVariable("phoneId") Long phoneId) {
        Phone phone = phoneService.getPhone(phoneId).orElseThrow(PhoneNotFoundException::new);
        model.addAttribute("phone", phone);
        return "productDetails";
    }
}
