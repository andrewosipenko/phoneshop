package com.es.phoneshop.web.controller.pages;


import com.es.core.dao.phoneDao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    private PhoneDao phoneDao;

    @Autowired
    public ProductListPageController(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("phones", phoneDao.findAll(0, 10));
        return "productList";
    }
}
