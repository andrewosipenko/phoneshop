package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(name="offset", defaultValue="0") int offset,
            @RequestParam(name="limit", defaultValue="10") int limit,
            @RequestParam(name="sortBy", defaultValue="model") String sortBy ,
            Model model) {
        System.out.printf("o:%s, l:%s, sb:%s\n", offset, limit, sortBy);
        model.addAttribute("phones", phoneDao.findAll(offset, limit, sortBy));
        return "productList";
    }

    @RequestMapping(method = RequestMethod.GET, params = "keyWord")
    public String searchProduct(
            @RequestParam(name="keyWord") String keyWord,
            @RequestParam(name="offset", defaultValue="0") int offset,
            @RequestParam(name="limit", defaultValue="10") int limit,
            @RequestParam(name="sortBy", defaultValue="model") String sortBy ,
            Model model) {
        System.out.printf("o:%s, l:%s, sb:%s\n", offset, limit, sortBy);
        model.addAttribute("phones", phoneDao.searchByModel(keyWord, limit, offset, sortBy));
        return "productList";
    }
}
