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

    private static final String ATTRIBUTE_ORDER_BY_BRAND = "brandOrder";

    private static final String ATTRIBUTE_ORDER_BY_MODEL = "modelOrder";

    private static final String ATTRIBUTE_ORDER_BY_COLOR = "colorOrder";

    private static final String ATTRIBUTE_ORDER_BY_DISPLAY_SIZE_INCHES = "displaySizeInchesOrder";

    private static final String ATTRIBUTE_ORDER_BY_PRICE = "priceOrder";

    private static final String DEFAULT_VALUE_PAGE = "1";

    private static final String DEFAULT_VALUE_ORDER = "phoneId asc";

    private static final String DEFAULT_VALUE_PHONE_MODEL = "";

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(value = "page", defaultValue = DEFAULT_VALUE_PAGE) int page,
                                  @RequestParam(value = "order", defaultValue = DEFAULT_VALUE_ORDER) String orderBy,
                                  @RequestParam(value = "model", defaultValue = DEFAULT_VALUE_PHONE_MODEL) String phoneModel) {

        if (phoneModel.equals(DEFAULT_VALUE_PHONE_MODEL)) {
            model.addAttribute(ATTRIBUTE_PHONES_LIST, phoneDao.findAllInOrder(orderBy,
                                                                              AMOUNT_PRODUCTS_ON_PAGE,
                                                                              AMOUNT_PRODUCTS_ON_PAGE * (page - 1)));
            model.addAttribute(ATTRIBUTE_PAGE_COUNT, (int) Math.ceil(phoneDao.productsCount() / (double) AMOUNT_PRODUCTS_ON_PAGE));
        } else {
            model.addAttribute(ATTRIBUTE_PHONES_LIST, phoneDao.findByModelInOrder(phoneModel,
                                                                                  orderBy,
                                                                                  AMOUNT_PRODUCTS_ON_PAGE,
                                                                                  AMOUNT_PRODUCTS_ON_PAGE * (page - 1)));
            model.addAttribute(ATTRIBUTE_PAGE_COUNT, (int) Math.ceil(phoneDao.productsCountWithModel(phoneModel) / (double) AMOUNT_PRODUCTS_ON_PAGE));
        }

        setAttributesForOrderBy(model, orderBy);

        return "productList";
    }

    private void setAttributesForOrderBy(Model model, String orderBy) {
        model.addAttribute(ATTRIBUTE_ORDER_BY_BRAND, "asc");
        model.addAttribute(ATTRIBUTE_ORDER_BY_MODEL, "asc");
        model.addAttribute(ATTRIBUTE_ORDER_BY_COLOR, "asc");
        model.addAttribute(ATTRIBUTE_ORDER_BY_DISPLAY_SIZE_INCHES, "asc");
        model.addAttribute(ATTRIBUTE_ORDER_BY_PRICE, "asc");

        if (!orderBy.equals(DEFAULT_VALUE_ORDER)) {
            String[] split = orderBy.split(" ");
            String elementForOrder = split[0];
            String order = split[split.length - 1];

            if (order.equals("asc")) {
                order = "desc";
            } else {
                order = "asc";
            }

            switch (elementForOrder) {
                case ("brand"):
                    model.addAttribute(ATTRIBUTE_ORDER_BY_BRAND, order);
                    break;
                case ("model"):
                    model.addAttribute(ATTRIBUTE_ORDER_BY_MODEL, order);
                    break;
                case ("color"):
                    model.addAttribute(ATTRIBUTE_ORDER_BY_COLOR, order);
                    break;
                case ("displaySizeInches"):
                    model.addAttribute(ATTRIBUTE_ORDER_BY_DISPLAY_SIZE_INCHES, order);
                    break;
                case ("price"):
                    model.addAttribute(ATTRIBUTE_ORDER_BY_PRICE, order);
                    break;
            }
        }
    }

}
