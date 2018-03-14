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

    private final static String DEFAULT_LIMIT = "10";
    private final static String DEFAULT_SORT = "model";

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="sortBy", defaultValue = DEFAULT_SORT) String sortBy ,
            @RequestParam(name="limit", defaultValue = DEFAULT_LIMIT) int limit,
            Model model) {
        int offset = (page-1)*limit;
        model.addAttribute("phones", phoneDao.findAll(offset, limit, sortBy));
        model.addAttribute("phonesAmount", phoneDao.countAll());

        return "productList";
    }

    @RequestMapping(method = RequestMethod.GET, params = "search")
    public String searchProduct(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="sortBy", defaultValue = DEFAULT_SORT) String sortBy ,
            @RequestParam(name="search") String search,
            @RequestParam(name="limit", defaultValue = DEFAULT_LIMIT) int limit,
            Model model) {
        int offset = (page-1)*limit;
        model.addAttribute("phones", phoneDao.searchByModel(search, limit, offset, sortBy));
        model.addAttribute("phonesAmount", phoneDao.countSearchResult(search));

        return "productList";
    }
}
