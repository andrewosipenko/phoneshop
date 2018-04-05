package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    @Resource
    private CartService cartService;

    @Resource
    private PhoneService phoneService;

    private final static String DEFAULT_LIMIT = "10";
    private final static String DEFAULT_SORT = "model";

    @GetMapping
    public String showProductList(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="sortBy", defaultValue = DEFAULT_SORT) String sortBy ,
            @RequestParam(name="limit", defaultValue = DEFAULT_LIMIT) int limit,
            Model model) {
        int offset = (page-1)*limit;
        model.addAttribute("phones", phoneService.findAll(offset, limit, sortBy));
        model.addAttribute("phonesAmount", phoneService.countAll());

        return "productList";
    }

    @GetMapping(params = "search")
    public String searchProduct(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="sortBy", defaultValue = DEFAULT_SORT) String sortBy ,
            @RequestParam(name="search") String search,
            @RequestParam(name="limit", defaultValue = DEFAULT_LIMIT) int limit,
            Model model) {
        int offset = (page-1)*limit;
        model.addAttribute("phones", phoneService.searchByModel(search, limit, offset, sortBy));
        model.addAttribute("phonesAmount", phoneService.countSearchResult(search));

        return "productList";
    }

    @ModelAttribute
    public void addCart(Model model){
        model.addAttribute("cartBill", cartService.getCart().getBill());
        model.addAttribute("cartQuantity", cartService.getCart().getQuantity());
    }
}
