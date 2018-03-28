package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.OrderEnum;
import com.es.phoneshop.web.controller.constants.ProductListConstants;
import com.es.phoneshop.web.service.ProductListPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import static com.es.phoneshop.web.controller.constants.ProductListConstants.CURRENT_PAGE_NUMBER;
import static com.es.phoneshop.web.controller.constants.ProductListConstants.PRODUCT_PAGE;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private ProductListPageService pageService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(defaultValue = "BRAND") String order,
                                  @RequestParam(name = CURRENT_PAGE_NUMBER, defaultValue = "1") int pageNumber,
                                  Model model) {
        model.addAttribute(PRODUCT_PAGE, pageService.getCurrentPage(0, 200,
                OrderEnum.valueOf(order.toUpperCase()), pageNumber));
        model.addAttribute(ProductListConstants.ORDER_BY_ATTRIBUTE, OrderEnum.valueOf(order.toUpperCase()));
        return "productList";
    }
}
