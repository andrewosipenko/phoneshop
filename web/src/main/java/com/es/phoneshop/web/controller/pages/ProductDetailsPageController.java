package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.phone.dao.util.SortBy;
import com.es.phoneshop.core.phone.model.Phone;
import com.es.phoneshop.core.phone.service.PhoneService;
import com.es.phoneshop.web.controller.throwable.PhoneNotFoundException;
import com.es.phoneshop.web.controller.util.AddToCartForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model, @RequestParam Long phoneId) {
        Phone phone = phoneService.getPhone(phoneId).orElseThrow(PhoneNotFoundException::new);
        model.addAttribute("phone", phone);
        model.addAttribute("addToCartForm", new AddToCartForm());
        return "productDetails";
    }
}
