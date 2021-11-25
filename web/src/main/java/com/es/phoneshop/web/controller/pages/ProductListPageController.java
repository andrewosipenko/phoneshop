package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    private static final int PHONES_COUNT_BY_PAGE = 10;
    private static final int MAX_PAGES_NUMBER = 9;
    private static final String PHONES_ATTRIBUTE = "phones";
    private static final String MAX_PAGE_ATTRIBUTE = "maxPage";
    private static final String PAGE_NUMBERS_ATTRIBUTE = "pageNumbers";
    private static final String QUERY_PARAM = "query";
    private static final String SORT_FIELD_PARAM = "sortField";
    private static final String SORT_ORDER_PARAM = "sortOrder";

    @RequestMapping(method = RequestMethod.GET, path = "/{page}")
    public String showProductList(Model model, @PathVariable int page,
                                  @RequestParam(required = false) Map<String, String> params,
                                  HttpSession session) {
        int offset = PHONES_COUNT_BY_PAGE * (page - 1);
        List<Phone> phones = phoneDao.findAllInStock(params.get(QUERY_PARAM),
                params.get(SORT_FIELD_PARAM), params.get(SORT_ORDER_PARAM));
        int maxPage = phones.size() % PHONES_COUNT_BY_PAGE == 0 ? phones.size() / PHONES_COUNT_BY_PAGE :
                phones.size() / PHONES_COUNT_BY_PAGE + 1;
        if (phones.size() != 0) {
            if (page == maxPage) {
                model.addAttribute(PHONES_ATTRIBUTE, phones.subList(offset, phones.size()));
            } else {
                model.addAttribute(PHONES_ATTRIBUTE, phones.subList(offset, offset + PHONES_COUNT_BY_PAGE));
            }
            model.addAttribute(PAGE_NUMBERS_ATTRIBUTE, makePageNumbers(page, maxPage));
            model.addAttribute(MAX_PAGE_ATTRIBUTE, maxPage);
        }
        model.addAttribute(HttpSessionCartService.CART_SESSION_ATTRIBUTE, cartService.getCart(session));
        return "productList";
    }

    private static Deque<Integer> makePageNumbers(int page, int maxPage) {
        Deque<Integer> pageNumbers = new LinkedList<>();
        pageNumbers.add(page);
        int nextPage = page + 1;
        int prevPage = page - 1;
        while (pageNumbers.size() < MAX_PAGES_NUMBER && !(nextPage > maxPage && prevPage < 1)) {
            if (nextPage <= maxPage) {
                pageNumbers.addLast(nextPage++);
            }
            if (prevPage >= 1) {
                pageNumbers.addFirst(prevPage--);
            }
        }
        return pageNumbers;
    }
}
