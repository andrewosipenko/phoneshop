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

    private static final int AMOUNT_PRODUCTS_ON_PAGE = 10;

    private static final String ATTRIBUTE_PHONES_LIST = "phones";

    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "order", defaultValue = "brand, model asc") String orderBy,
                                  @RequestParam(value = "model", required = false) String phoneModel) {

        if (phoneModel != null && !phoneModel.isEmpty()) {
            model.addAttribute(ATTRIBUTE_PHONES_LIST, phoneDao.findByModelInOrder(phoneModel,
                                                                                  orderBy,
                                                                                  AMOUNT_PRODUCTS_ON_PAGE,
                                                                                  AMOUNT_PRODUCTS_ON_PAGE * (page - 1)));
            model.addAttribute(ATTRIBUTE_PAGE_COUNT, (int) Math.ceil(phoneDao.productsCountWithModel(phoneModel) / (double) AMOUNT_PRODUCTS_ON_PAGE));
        } else {
            model.addAttribute(ATTRIBUTE_PHONES_LIST, phoneDao.findAllInOrder(orderBy,
                                                                              AMOUNT_PRODUCTS_ON_PAGE,
                                                                              AMOUNT_PRODUCTS_ON_PAGE * (page - 1)));
            model.addAttribute(ATTRIBUTE_PAGE_COUNT, (int) Math.ceil(phoneDao.productsCount() / (double) AMOUNT_PRODUCTS_ON_PAGE));
        }

        return "productList";
    }

}
