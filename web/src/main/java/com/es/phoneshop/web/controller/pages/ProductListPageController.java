package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.Phone;
import com.es.core.services.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    private static final Integer AMOUNT_OF_SHOWED_PRODUCTS = 10;
    @Resource
    private PhoneService phoneService;

    @GetMapping()
    public String doGet(Model model) {
        return "redirect:/productList/1";
    }

    @GetMapping("/{pageId}")
    public String showProductList(@PathVariable Integer pageId, Model model) {
        model.addAttribute("phones", findPhonesForCurrentPage(pageId));
        return "productList";
    }

    @PostMapping("/{pageId}")
    public String doPost(@PathVariable Integer pageId, Model model) {
        return "redirect:/productList/"+pageId;
    }

    private List<Phone> findPhonesForCurrentPage(Integer pageId) {
        Long totalAmountOfProductsOnStock = phoneService.getTotalAmountOfPhonesWithPositiveStock();
        if (AMOUNT_OF_SHOWED_PRODUCTS * (pageId - 1) > totalAmountOfProductsOnStock) {
            pageId = ((Long) (totalAmountOfProductsOnStock / AMOUNT_OF_SHOWED_PRODUCTS)).intValue();
        } else if (pageId == 0) {
            pageId = 1;
        }
        return phoneService.getPhonesWithPositiveStock(AMOUNT_OF_SHOWED_PRODUCTS * (pageId - 1), AMOUNT_OF_SHOWED_PRODUCTS);
    }
}
