package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneService;
import com.es.phoneshop.web.util.CartUtils;
import com.es.phoneshop.web.validate.sort.Column;
import com.es.phoneshop.web.validate.sort.Order;
import com.es.phoneshop.web.validate.sort.ValidateOrderBy;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.Resource;

@Controller
@RequestMapping (value = "/productList")
@Validated
public class ProductListPageController {

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    private static final int AMOUNT_PRODUCTS_ON_PAGE = 10;

    private static final String ATTRIBUTE_PHONES_LIST = "phones";
    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";
    private static final String ATTRIBUTE_CART_STATUS = "cartStatus";

    private static final String DEFAULT_VALUE_PAGE = "1";
    private static final String DEFAULT_VALUE_ORDER = "phoneId_asc";

    @GetMapping
    public String showProductList(Model model,
                                  @ValidateOrderBy(columnEnum = Column.class, orderEnum = Order.class)
                                  @RequestParam(value = "order", defaultValue = DEFAULT_VALUE_ORDER)
                                  String orderBy,
                                  @Range(min = 1)
                                  @RequestParam(value = "page", defaultValue = DEFAULT_VALUE_PAGE)
                                  int page,
                                  @RequestParam(value = "model", required = false) String phoneModel) {
        if (phoneModel == null) {
            model.addAttribute(ATTRIBUTE_PHONES_LIST, phoneService.findInOrder(orderBy,
                                                                               AMOUNT_PRODUCTS_ON_PAGE * (page - 1),
                                                                               AMOUNT_PRODUCTS_ON_PAGE));
            model.addAttribute(ATTRIBUTE_PAGE_COUNT, getPageCount(phoneService.productsCount()));
        } else {
            model.addAttribute(ATTRIBUTE_PHONES_LIST, phoneService.findByModelInOrder(phoneModel,
                                                                                      orderBy,
                                                                                      AMOUNT_PRODUCTS_ON_PAGE * (page - 1),
                                                                                      AMOUNT_PRODUCTS_ON_PAGE));
            model.addAttribute(ATTRIBUTE_PAGE_COUNT, getPageCount(phoneService.productsCountByModel(phoneModel)));
        }

        CartUtils.addCartStatusInModel(cartService, model, ATTRIBUTE_CART_STATUS);
        return "productList";
    }

    @ExceptionHandler(value = {MethodConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    public String handleValidationFail() {
        return "redirect:/productList";
    }

    private int getPageCount(double productsCount) {
        return (int) Math.ceil(productsCount / AMOUNT_PRODUCTS_ON_PAGE);
    }
}
