package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.ControllerConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    @Resource
    private PhoneDao phoneDao;

    private static int AMOUNT_PHONES_ON_PAGE = 10;

    private static final int FIRST_PAGE_NUMBER = 1;

    private static final int AROUND_PAGES_COUNT = 3;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(defaultValue = ControllerConstants.BRAND_REQUEST_PARAM) PhoneDao.OrderBy order,
                                  @RequestParam(name = ControllerConstants.PAGE_NUMBER_REQUEST_PARAM, defaultValue = "1") int pageNumber,
                                  @RequestParam(required = false) String query, Model model) throws UnsupportedEncodingException {

        int phonesCount = getPhoneCount(query);
        int pagesCount = getPagesCount(phonesCount);
        pageNumber = normalizePageNumber(pageNumber, phonesCount);

        int offset = ((pageNumber - 1) * AMOUNT_PHONES_ON_PAGE);
        int limit = AMOUNT_PHONES_ON_PAGE;

        List<Phone> phones = getPhoneList(query, order, offset, limit);

        model.addAttribute(ControllerConstants.PHONE_COUNT_ATTRIBUTE, phonesCount);
        model.addAttribute(ControllerConstants.PAGE_ATTRIBUTE, pageNumber);
        model.addAttribute(ControllerConstants.PAGE_COUNT_ATTRIBUTE, pagesCount);
        model.addAttribute(ControllerConstants.START_PAGINATION_NUMBER_ATTRIBUTE, Math.max(pageNumber - AROUND_PAGES_COUNT, FIRST_PAGE_NUMBER));
        model.addAttribute(ControllerConstants.FINISH_PAGINATION_NUMBER_ATTRIBUTE, Math.min(pageNumber + AROUND_PAGES_COUNT, pagesCount));
        model.addAttribute(ControllerConstants.PHONE_LIST_ATTRIBUTE, phones);
        model.addAttribute(ControllerConstants.SORT_ORDER_ATTRIBUTE, order.name());
        if (query != null) {
            model.addAttribute(ControllerConstants.SEARCH_QUERY_ATTRIBUTE, encodeValue(query));
        }
        return ControllerConstants.PRODUCT_LIST_PAGE_NAME;
    }


    private int getPhoneCount(String query) {
        if (query == null) {
            return phoneDao.phonesCount();
        }
        return phoneDao.phonesCountByQuery(query);
    }

    private List<Phone> getPhoneList(String query, PhoneDao.OrderBy order, int offset, int limit) {
        if (query == null) {
            return phoneDao.findAllInOrder(order, offset, limit);
        }
        return phoneDao.getPhonesByQuery(query, order, offset, limit);
    }

    private int normalizePageNumber(int pageNumber, int phonesCount) {
        pageNumber = Math.max(pageNumber, FIRST_PAGE_NUMBER);
        int lastPageNumber = getPagesCount(phonesCount);
        pageNumber = Math.min(pageNumber, lastPageNumber);
        return pageNumber;
    }

    private int getPagesCount(int phonesCount) {
        if (phonesCount == 0) {
            return 1;
        }
        return (int) Math.ceil((double) phonesCount / AMOUNT_PHONES_ON_PAGE);
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
