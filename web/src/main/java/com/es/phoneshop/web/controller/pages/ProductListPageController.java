package com.es.phoneshop.web.controller.pages;


import com.es.core.model.SqlOrderByKeyword;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.ProductPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {


    private static final String PRODUCT_PAGE_ATTRIBUTE = "productPage";
    private static final String DEFAULT_SORT_BY_VALUE = "brand";
    private static final String DEFAULT_ORDER = "ASC";
    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String CART_ATTRIBUTE = "infoCart";

    @Resource
    private ProductPageService productPageService;

    @Resource
    private CartService cartService;


    @GetMapping
    public String showProductList(@RequestParam(defaultValue = DEFAULT_SORT_BY_VALUE) String sortBy,
                                  @RequestParam(defaultValue = DEFAULT_ORDER) SqlOrderByKeyword sqlOrderByKeyword,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(required = false) String query,
                                  Model model) {
        model.addAttribute(PRODUCT_PAGE_ATTRIBUTE, productPageService.generateProductPage(query, sortBy, sqlOrderByKeyword, page));
        model.addAttribute(CART_ATTRIBUTE, cartService.getInfoCart());
        return "productList";
    }
}
