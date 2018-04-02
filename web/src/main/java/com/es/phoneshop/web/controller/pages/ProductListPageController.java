package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.phoneshop.web.validate.sort.Column;
import com.es.phoneshop.web.validate.sort.Order;
import com.es.phoneshop.web.validate.sort.OrderByValid;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping (value = "/productList")
@Validated
public class ProductListPageController {
    @Resource
    private PhoneService phoneService;

    private static final int AMOUNT_PRODUCTS_ON_PAGE = 10;

    private static final String ATTRIBUTE_PHONES_LIST = "phones";
    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";

    private static final String DEFAULT_VALUE_PAGE = "1";
    private static final String DEFAULT_VALUE_ORDER = "phoneId_asc";

    @GetMapping
    public String showProductList(Model model,
                                  @OrderByValid(columnEnum = Column.class, orderEnum = Order.class)
                                  @RequestParam(value = "order", defaultValue = DEFAULT_VALUE_ORDER)
                                  String orderBy,
                                  @Range(min = 1)
                                  @RequestParam(value = "page", defaultValue = DEFAULT_VALUE_PAGE)
                                  int page,
                                  @RequestParam(value = "model", required = false)
                                  String phoneModel) {
        List<Phone> phones;
        long pageCount;
        if (phoneModel == null) {
            phones = phoneService.findInOrder(orderBy, AMOUNT_PRODUCTS_ON_PAGE * (page - 1), AMOUNT_PRODUCTS_ON_PAGE);
            pageCount = getPageCount(phoneService.productsCount());
        } else {
            phones = phoneService.findByModelInOrder(phoneModel, orderBy, AMOUNT_PRODUCTS_ON_PAGE * (page - 1), AMOUNT_PRODUCTS_ON_PAGE);
            pageCount = getPageCount(phoneService.productsCountByModel(phoneModel));
        }

        model.addAttribute(ATTRIBUTE_PHONES_LIST, phones);
        model.addAttribute(ATTRIBUTE_PAGE_COUNT, pageCount);

        return "productList";
    }

    @ExceptionHandler(MethodConstraintViolationException.class)
    public String handleValidationFail() {
        return "redirect:/productList";
    }

    private long getPageCount(double productsCount) {
        return (long) Math.ceil(productsCount / AMOUNT_PRODUCTS_ON_PAGE);
    }
}
