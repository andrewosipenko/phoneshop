package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.facade.ProductListFacade;
import com.es.core.form.ProductListPageForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductListPageController {

    @Resource
    private ProductListFacade phoneDaoFacade;

    @GetMapping(value = "/productList")
    public String showProductList(@RequestParam(value = "query", required = false) String phoneNameQuery,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String orderToSort,
                                  @RequestParam(value = "fieldToSort", required = false, defaultValue = "brand") String fieldToSort,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  Model model) {
        ProductListPageForm productListPageForm = new ProductListPageForm(phoneNameQuery, fieldToSort, orderToSort, page);
        model.addAttribute("phones", phoneDaoFacade.showProductList(productListPageForm));
        return "productList";
    }
}
