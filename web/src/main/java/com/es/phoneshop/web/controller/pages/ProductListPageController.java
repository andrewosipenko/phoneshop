package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import com.es.core.util.OffsetPhoneToPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private final static String NUMBER_OF_PAGE_PARAMETER = "numberOfPage";
    private final static String PHONES_PARAMETER = "phones";
    private final static String PAGE_PARAMETER = "page";
    private final static String SEARCH_TEXT_PARAMETER = "text";
    private final static String SORT_PARAMETER = "sort";
    private final static String ORDER_PARAMETER = "order";
    private final static String TOTAL_PRICE = "totalPrice";
    private final static String COUNT_OF_CART_ITEM = "countOfCartItems";

    private final static int PAGE_SIZE = 10;
    private final static String PRODUCT_LIST_PAGE = "productList";

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    @RequestMapping(params = {PAGE_PARAMETER}, method = RequestMethod.GET)
    public String showProductList(@RequestParam(PAGE_PARAMETER) int page, Model model) {
        int offset = OffsetPhoneToPage.getOffsetToPage(page);
        int numberOfPage = phoneService.findPageCount(PAGE_SIZE);
        List<Phone> phones = phoneService.findActivePhonesByPage(offset, PAGE_SIZE);

        setCartParameterToModel(model, page, numberOfPage, phones);

        return PRODUCT_LIST_PAGE;
    }

    @RequestMapping(params = {PAGE_PARAMETER, SEARCH_TEXT_PARAMETER}, method = RequestMethod.GET)
    public String searchInProductList(@RequestParam(PAGE_PARAMETER) int page,
                                      @RequestParam(SEARCH_TEXT_PARAMETER) String text,
                                      Model model) {
        int numberOfPage = phoneService.findPageCountWithSearchText(PAGE_SIZE, text);
        int newPage = OffsetPhoneToPage.getPageWithSearch(numberOfPage, page);
        int offset = OffsetPhoneToPage.getOffsetToPage(page);
        List<Phone> phones = phoneService.findPhonesLikeSearchText(offset, PAGE_SIZE, text);

        model.addAttribute(SEARCH_TEXT_PARAMETER, text);
        setCartParameterToModel(model, newPage, numberOfPage, phones);

        return PRODUCT_LIST_PAGE;
    }

    @RequestMapping(params = {PAGE_PARAMETER, SORT_PARAMETER, ORDER_PARAMETER}, method = RequestMethod.GET)
    public String orderProductList(@RequestParam(PAGE_PARAMETER) int page,
                                   @RequestParam(ORDER_PARAMETER) String order,
                                   @RequestParam(SORT_PARAMETER) String sort,
                                   Model model) {
        int offset = OffsetPhoneToPage.getOffsetToPage(page);
        int numberOfPage = phoneService.findPageCount(PAGE_SIZE);
        List<Phone> phones = phoneService.sortPhones(offset, PAGE_SIZE, sort, order);

        setCartParameterToModel(model, page, numberOfPage, phones);
        model.addAttribute(SORT_PARAMETER, sort);
        model.addAttribute(ORDER_PARAMETER, order);

        return PRODUCT_LIST_PAGE;
    }

    @RequestMapping(params = {PAGE_PARAMETER, SORT_PARAMETER, ORDER_PARAMETER, SEARCH_TEXT_PARAMETER}, method = RequestMethod.GET)
    public String orderSearchInProductList(@RequestParam(PAGE_PARAMETER) int page,
                                           @RequestParam(SEARCH_TEXT_PARAMETER) String query,
                                           @RequestParam(ORDER_PARAMETER) String order,
                                           @RequestParam(SORT_PARAMETER) String sort,
                                           Model model) {
        int numberOfPage = phoneService.findPageCountWithSearchText(PAGE_SIZE, query);
        int newPage = OffsetPhoneToPage.getPageWithSearch(numberOfPage, page);
        int offset = OffsetPhoneToPage.getOffsetToPage(page);
        List<Phone> phones = phoneService.sortPhonesLikeSearchText(offset, PAGE_SIZE, sort, order, query);

        setCartParameterToModel(model, newPage, numberOfPage, phones);
        model.addAttribute(SORT_PARAMETER, sort);
        model.addAttribute(ORDER_PARAMETER, order);
        model.addAttribute(SEARCH_TEXT_PARAMETER, query);

        return PRODUCT_LIST_PAGE;
    }

    private void setCartParameterToModel(Model model, int page, int numberOfPage, List<Phone> phones) {
        Cart cart = cartService.getCart();
        model.addAttribute(COUNT_OF_CART_ITEM, cart.getCartItems().size());
        model.addAttribute(TOTAL_PRICE, cart.getTotalPrice());
        model.addAttribute(NUMBER_OF_PAGE_PARAMETER, numberOfPage);
        model.addAttribute(PAGE_PARAMETER, page);
        model.addAttribute(PHONES_PARAMETER, phones);
    }
}
