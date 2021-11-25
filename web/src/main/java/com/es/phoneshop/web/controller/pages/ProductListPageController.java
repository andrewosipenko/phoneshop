package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartService;
import com.es.core.model.enums.SortField;
import com.es.core.model.enums.SortOrder;
import com.es.core.model.phone.PhoneDao;
import com.es.core.service.PaginationService;
import com.es.core.service.PaginationServiceImpl;
import com.es.phoneshop.web.controller.pages.dto.CartAddForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String SORT_FIELD = "sortField";
    public static final String SORT_ORDER = "sortOrder";
    public static final String SEARCH_TEXT = "searchText";
    public static final String PAGE_NUMBER_DEFAULT = "1";
    public static final String SORT_FIELD_DEFAULT = "BRAND";
    public static final String SORT_ORDER_DEFAULT = "ASCENDING";
    public static final String PAGINATION_LIST = "paginationList";
    public static final String PHONES = "phones";
    public static final String CART = "cart";
    public static final String CART_ADD_FORM = "cartAddForm";
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private PaginationService paginationService;
    @Resource
    private CartService cartService;

    @ModelAttribute
    public void addCartAddForm(Model model) {
        model.addAttribute(CART_ADD_FORM, new CartAddForm());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  final @RequestParam(name = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT) Integer pageNumber,
                                  final @RequestParam(name = SORT_FIELD, defaultValue = SORT_FIELD_DEFAULT) SortField sortField,
                                  final @RequestParam(name = SORT_ORDER, defaultValue = SORT_ORDER_DEFAULT) SortOrder sortOrder,
                                  final @RequestParam(name = SEARCH_TEXT, defaultValue = "") String searchText) {
        model.addAttribute(PAGINATION_LIST, paginationService.getPaginationList(pageNumber));
        model.addAttribute(PAGE_NUMBER, pageNumber);
        model.addAttribute(PHONES, phoneDao.findAll(paginationService.getOffset(pageNumber),
                PaginationServiceImpl.PHONES_PER_PAGE, sortField, sortOrder, searchText));
        model.addAttribute(SEARCH_TEXT, searchText);
        model.addAttribute(SORT_FIELD, sortField);
        model.addAttribute(SORT_ORDER, sortOrder);
        model.addAttribute(CART, cartService.getCart());
        return "productList";
    }
}
