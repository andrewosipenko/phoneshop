package com.es.phoneshop.web.controller.pages;

import com.es.facade.ProductDetailsPageFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class ProductDetailsPageController {

    @Resource
    private ProductDetailsPageFacade productDetailsPageFacade;

    @GetMapping(value = "/productDetailsPage/productId={productId}")
    public String getProductDetailPage(@PathVariable(name = "productId", required = false) Long phoneId, Model model) {
        model.addAttribute("phone", productDetailsPageFacade.getProductDetailPage(phoneId));
        return "productDetailsPage";
    }
}
