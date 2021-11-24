package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    private static final int PHONES_COUNT_BY_PAGE = 10;

    @RequestMapping(method = RequestMethod.GET, path = "/{page}")
    public String showProductList(Model model, @PathVariable int page, @RequestParam(required = false) String query) {
        int offset = PHONES_COUNT_BY_PAGE * (page - 1);
        model.addAttribute("phones", phoneDao.findAllInStock(offset, PHONES_COUNT_BY_PAGE, query));
        return "productList";
    }
}
