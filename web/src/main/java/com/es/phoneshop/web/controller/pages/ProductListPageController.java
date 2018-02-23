package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.OrderBy;
import com.es.phoneshop.web.service.PhonePageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import static com.es.phoneshop.web.constant.ControllerConstants.PRODUCT_LIST_PAGE_NAME;
import static com.es.phoneshop.web.constant.ControllerMapping.PRODUCT_LIST_PAGE;

@Controller
@RequestMapping(PRODUCT_LIST_PAGE)
public class ProductListPageController {

    private final static String BRAND_REQUEST_PARAM = "brand";

    private final static String PAGE_NUMBER_REQUEST_PARAM = "page";

    private final static String SORT_ORDER_ATTRIBUTE = "order";

    private final static String SEARCH_QUERY_ATTRIBUTE = "query";

    private final static String PRODUCT_PAGE_ATTRIBUTE = "productPage";

    @Resource
    private PhonePageService phonePageService;

    @GetMapping
    public String showProductList(@RequestParam(defaultValue = BRAND_REQUEST_PARAM) OrderBy order,
                                  @RequestParam(name = PAGE_NUMBER_REQUEST_PARAM, defaultValue = "1") int pageNumber,
                                  @ModelAttribute(SEARCH_QUERY_ATTRIBUTE) String query, Model model) {

        model.addAttribute(PRODUCT_PAGE_ATTRIBUTE, phonePageService.getPhonePage(order, query, pageNumber));
        model.addAttribute(SORT_ORDER_ATTRIBUTE, order);
        return PRODUCT_LIST_PAGE_NAME;
    }


}
