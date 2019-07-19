package com.es.phoneshop.web.controller.pages;

import com.es.core.model.ProductDao;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {

    @Resource
    private PhoneDao phoneDao;

    @Autowired
    private ProductDao productDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model) {
        productDao.load(1010L);
        model.addAttribute("phones", phoneDao.findAll(0, 10));
        return "productList";
    }

}
