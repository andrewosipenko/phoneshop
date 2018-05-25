package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.es.core.model.phone.OrderEnum;
import com.es.phoneshop.web.bean.cart.CartItemInfo;
import com.es.phoneshop.web.service.ProductListPageService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import static com.es.phoneshop.web.controller.constants.ControllerConstants.ProductListConstants.*;


@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private ProductListPageService pageService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(defaultValue = "BRAND") String order,
                                  @RequestParam(name = CURRENT_PAGE_NUMBER, defaultValue = "1") int pageNumber,
                                  @RequestParam(name = SEARCH_QUERY_STRING, defaultValue = "") String query,
                                  Model model) throws IllegalArgumentException {
        model.addAttribute(PRODUCT_PAGE, pageService.getCurrentPage(OrderEnum.valueOf(order.toUpperCase()), query, pageNumber));
        model.addAttribute(ORDER_BY_ATTRIBUTE, OrderEnum.valueOf(order.toUpperCase()));
        model.addAttribute(SEARCH_QUERY_STRING, query);
        model.addAttribute(CART_ITEM, new CartItemInfo());
        return "productList";
    }
}
