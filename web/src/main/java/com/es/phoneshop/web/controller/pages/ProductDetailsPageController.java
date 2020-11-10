package com.es.phoneshop.web.controller.pages;

import com.es.core.model.DAO.phone.PhoneDao;
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
    private PhoneDao phoneDao;

    @GetMapping(value = "/{phoneId}")
    public String showProductsDetails(@PathVariable String phoneId, Model model) {
        model.addAttribute(phoneDao.get(Long.parseLong(phoneId)).get());
        return "productDetails";
    }

}
