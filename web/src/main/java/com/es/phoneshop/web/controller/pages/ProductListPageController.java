package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.service.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Past;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private final static String NUMBER_OF_PAGE_PARAMETER = "numberOfPage";
    private final static String PHONES_PARAMETER = "phones";
    private final static String PAGE_PARAMETER = "page";
    private final static String QUERY_PARAMETER = "query";
    private final static String SORT_PARAMETER = "sort";
    private final static String ORDER_PARAMETER = "order";

    private final static int PAGE_SIZE = 10;
    private final static String PAGE_URL = "productList";
    private final static int START_SEARCH_PAGE = 1;

    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(params = {"page"}, method = RequestMethod.GET)
    public String showProductList(@RequestParam("page") int page, Model model) {
        model.addAttribute(PAGE_PARAMETER, page);
        int offset = getOffset(page);
        model.addAttribute(PHONES_PARAMETER, phoneDao.findActivePhonesByPage(offset, PAGE_SIZE));
        model.addAttribute(NUMBER_OF_PAGE_PARAMETER, phoneDao.findPageCount(PAGE_SIZE));
        return PAGE_URL;
    }

    @RequestMapping(params = {"page", "query"}, method = RequestMethod.GET)
    public String searchInProductList(@RequestParam("page") int page,
                                      @RequestParam("query") String query,
                                      Model model) {
        page = START_SEARCH_PAGE;
        model.addAttribute(PAGE_PARAMETER, page);
        int offset = getOffset(page);
        if (query == null || query.isEmpty()) {
            model.addAttribute(NUMBER_OF_PAGE_PARAMETER, phoneDao.findPageCount(PAGE_SIZE));
            model.addAttribute(PHONES_PARAMETER, phoneDao.findActivePhonesByPage(offset, PAGE_SIZE));
        } else {
            model.addAttribute(NUMBER_OF_PAGE_PARAMETER, phoneDao.findPageCountWithQuery(PAGE_SIZE, query));
            model.addAttribute(PHONES_PARAMETER,
                    phoneDao.findPhonesLikeQuery(offset, PAGE_SIZE, query));
            model.addAttribute(QUERY_PARAMETER, query);
        }
        return PAGE_URL;
    }

    @RequestMapping(params = {"page", "sort", "order"}, method = RequestMethod.GET)
    public String orderProductList(@RequestParam("page") int page,
                                      @RequestParam("order") String order,
                                      @RequestParam("sort") String sort,
                                      Model model) {
        model.addAttribute(PAGE_PARAMETER, page);
        model.addAttribute(SORT_PARAMETER, sort);
        model.addAttribute(ORDER_PARAMETER, order);
        int offset = getOffset(page);
        model.addAttribute(PHONES_PARAMETER, phoneDao.sortPhones(offset, PAGE_SIZE, sort, order));
        model.addAttribute(NUMBER_OF_PAGE_PARAMETER, phoneDao.findPageCount(PAGE_SIZE));
        return PAGE_URL;
    }

    @RequestMapping(params = {"page", "sort", "order", "query"}, method = RequestMethod.GET)
    public String orderSearchInProductList(@RequestParam("page") int page,
                                      @RequestParam("query") String query,
                                      @RequestParam("order") String order,
                                      @RequestParam("sort") String sort,
                                      Model model) {
        model.addAttribute(PAGE_PARAMETER, page);
        model.addAttribute(SORT_PARAMETER, sort);
        model.addAttribute(ORDER_PARAMETER, order);
        model.addAttribute(QUERY_PARAMETER, query);
        int offset = getOffset(page);
        model.addAttribute(PHONES_PARAMETER, phoneDao.sortPhonesLikeQuery(offset, PAGE_SIZE, sort, order, query));
        model.addAttribute(NUMBER_OF_PAGE_PARAMETER, phoneDao.findPageCountWithQuery(PAGE_SIZE, query));
        return PAGE_URL;
    }

    private int getOffset(int page) {
        return  page == 1 ? (page - 1) : (page - 1) * 10;
    }
}
